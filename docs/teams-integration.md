# Setting up Microsoft Teams Integration

To enable Microsoft Teams notifications for your test reports, follow these steps:

## Create a Teams Webhook

1. Open Microsoft Teams
2. Navigate to the channel where you want to receive notifications
3. Click on the "..." (more options) next to the channel name
4. Select "Connectors"
5. Find "Incoming Webhook" and click "Configure" 
6. Provide a name for the webhook (e.g., "API Test Reports")
7. Optionally upload an icon for the webhook
8. Click "Create"
9. Copy the webhook URL that is generated

## Add the Webhook URL to GitHub Secrets

1. Go to your GitHub repository
2. Navigate to "Settings" > "Secrets and variables" > "Actions"
3. Click "New repository secret"
4. Name: `MS_TEAMS_WEBHOOK_URI`
5. Value: Paste the webhook URL you copied from Teams
6. Click "Add secret"

Now your GitHub Actions workflow will send notifications to Microsoft Teams whenever tests are run!

## Testing the Integration

To test if your Teams integration is working:

1. Go to your GitHub repository
2. Navigate to "Actions"
3. Select the "Daily API Tests" workflow
4. Click "Run workflow"
5. Check "Send email report" if you want to test the email functionality as well
6. Click "Run workflow"

You should receive a notification in your Microsoft Teams channel shortly after the workflow completes.
