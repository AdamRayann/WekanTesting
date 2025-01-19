import puppeteer from 'puppeteer';
import lighthouse from 'lighthouse';
import fs from 'fs/promises';

(async () => {
  try {
    const browser = await puppeteer.launch({
      headless: true,
      args: ['--remote-debugging-port=9222'], // Required for Lighthouse to connect
    });

    const page = await browser.newPage();
    const targetUrl = 'https://ef35-84-110-182-34.ngrok-free.app/'; // Replace with your app's login page URL

    // Navigate to the login page
    await page.goto(targetUrl, { waitUntil: 'networkidle2', timeout: 60000 });

    // Perform the login steps
    await page.click('button');
    await page.waitForNavigation({ waitUntil: 'networkidle2', timeout: 60000 });
    await page.type('#at-field-username_and_email', 'admin1'); // Replace with the correct selector and username
    await page.type('#at-field-password', 'admin1'); // Replace with the correct selector and password
    await page.click('#at-btn'); // Replace with the correct selector for the login button
    await page.waitForNavigation({ waitUntil: 'networkidle2', timeout: 60000 });

    console.log('Logged in successfully!');

    const lighthouseUrl = page.url(); // The page URL after login
    console.log(`Lighthouse will analyze: ${lighthouseUrl}`);

    // Run Lighthouse
    const result = await lighthouse(lighthouseUrl, {
      port: 9222,
      output: ['html'], // Ensure report generation
      logLevel: 'info',
    });

    const reportHtml = result.report; // Lighthouse HTML report
    if (!reportHtml) {
      throw new Error('Lighthouse did not generate a report.');
    }

    // Save the Lighthouse report
    await fs.writeFile('lighthouse-report.html', reportHtml, 'utf-8');
    console.log('Lighthouse report saved as lighthouse-report.html');

    await browser.close();
  } catch (error) {
    console.error('Error running Lighthouse analysis:', error);
  }
})();
