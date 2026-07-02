const fs = require('fs');
const path = require('path');

// 1. Find all JSON files in target/site/serenity/
const serenityDir = path.join(process.cwd(), 'target', 'site', 'serenity');
const files = fs.readdirSync(serenityDir).filter(f => f.endsWith('.json') && !f.startsWith('SERENITY'));

// 2. Parse each JSON file into a test result object
const tests = files.map(file => {
    const raw = fs.readFileSync(path.join(serenityDir, file), 'utf8');
    return JSON.parse(raw);
}).filter(t => t.title && !t.title.startsWith('[')); // only keep actual test results

//sort by run time
tests.sort((a, b) => {
    return new Date(a.startTime) - new Date(b.startTime);
});

// 4. Helper color for tge badge result
function badgeColor(result) {
    if (result === 'SUCCESS') return '#28a745';
    if (result === 'FAILURE') return '#dc3545';
    return '#ffc107';
}

// 5. Helper format duration
function formatDuration(ms) {
    return ms < 1000 ? `${ms}ms` : `${(ms / 1000).toFixed(2)}s`;
}

// 6. Helper  get tags
function getTags(test) {
    return (test.tags || [])
        .filter(t => t.type === 'tag')
        .map(t => `<span style="background:#e9ecef;padding:2px 8px;border-radius:4px;font-size:12px;">${t.displayName}</span>`)
        .join(' ');
}

// 7. Summary counts
const total = tests.length;
const passed = tests.filter(t => t.result === 'SUCCESS').length;
const failed = tests.filter(t => t.result !== 'SUCCESS').length;

// 8. Build test cards HTML
const testCards = tests.map(test => {
    const stepsHtml = (test.testSteps || []).map(step => {
        const query = step?.restQuery;
        if (!query) return '';
        return `
        <div style="background:#f8f9fa;border-radius:6px;padding:12px;margin-top:10px;font-size:13px;">
            <div style="font-weight:1000;margin-bottom:12px;">Step ${step.number} : </div> 
            <div></div>
            <div><strong>${query.method}</strong> <span style="color:#555;">${query.path}</span></div>
            <div style="margin-top:6px;">Status: <strong>${query.statusCode}</strong></div>
            ${query.content ? `<div style="margin-top:6px;"><strong>Request Body:</strong><pre style="margin:4px 0;white-space:pre-wrap;font-size:12px;">${query.content}</pre></div>` : ''}
            ${query.responseBody ? `<div style="margin-top:6px;"><strong>Response Body:</strong><pre style="margin:4px 0;white-space:pre-wrap;font-size:12px;">${query.responseBody}</pre></div>` : ''}
        </div>`;
}).join('');
        return `
    <div style="background:white;border-radius:8px;padding:20px;margin-bottom:16px;box-shadow:0 1px 4px rgba(0,0,0,0.08);border-left:4px solid ${badgeColor(test.result)};">
        <div style="display:flex;justify-content:space-between;align-items:center;">
            <div>
                <span style="font-weight:600;font-size:15px;">${test.title}</span>
                <span style="margin-left:10px;">${getTags(test)}</span>
            </div>
            <div style="display:flex;gap:12px;align-items:center;">
                <span style="font-size:13px;color:#888;">${formatDuration(test.duration)}</span>
                <span style="background:${badgeColor(test.result)};color:white;padding:3px 10px;border-radius:4px;font-size:13px;">${test.result}</span>
            </div>
        </div>
        ${stepsHtml}
    </div>`;
}).join('');

// 9. Build full HTML page
const html = `<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Test Report — Public API Scenario Runner</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: Arial, sans-serif; background: #f4f6f8; color: #333; padding: 30px; }
        h1 { font-size: 22px; margin-bottom: 4px; }
        .subtitle { color: #888; font-size: 13px; margin-bottom: 24px; }
        .summary { display: flex; gap: 16px; margin-bottom: 28px; }
        .summary-card { background: white; border-radius: 8px; padding: 16px 24px; box-shadow: 0 1px 4px rgba(0,0,0,0.08); text-align: center; min-width: 100px; }
        .summary-card .number { font-size: 28px; font-weight: bold; }
        .summary-card .label { font-size: 12px; color: #888; margin-top: 4px; }
        pre { font-family: monospace; }
    </style>
</head>
<body>
    <h1>Test Report — Public API Scenario Runner</h1>
    <p class="subtitle">Generated on ${new Date().toLocaleString()}</p>
 
    <div class="summary">
        <div class="summary-card">
            <div class="number">${total}</div>
            <div class="label">Total</div>
        </div>
        <div class="summary-card">
            <div class="number" style="color:#28a745;">${passed}</div>
            <div class="label">Passed</div>
        </div>
        <div class="summary-card">
            <div class="number" style="color:#dc3545;">${failed}</div>
            <div class="label">Failed</div>
        </div>
    </div>
 
    ${testCards}
</body>
</html>`;

// 10. Write the output file
const outputPath = path.join(process.cwd(), 'target', 'site', 'serenity', 'custom-report.html');
fs.writeFileSync(outputPath, html);
console.log(`Report generated: ${outputPath}`);
