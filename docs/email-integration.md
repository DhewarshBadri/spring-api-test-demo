# Setting up SendGrid Email Integration

This guide explains how to set up SendGrid for sending test reports via email from your GitHub Actions workflow.

## Create a SendGrid Account

1. Go to [SendGrid's website](https://sendgrid.com/) and sign up for an account
2. Follow their verification process to set up your account

## Create an API Key

1. Log in to your SendGrid account
2. Navigate to "Settings" > "API Keys"
3. Click "Create API Key"
4. Name your API key (e.g., "GitHub Actions Email Reports")
5. Select "Restricted Access" and ensure the following permissions are enabled:
   - Mail Send > Full Access
6. Click "Create & View"
7. Copy the API key that is displayed (you won't be able to see it again)

## Configure Domain Authentication (Optional but Recommended)

For better email deliverability:

1. Navigate to "Settings" > "Sender Authentication"
2. Click "Authenticate Your Domain"
3. Follow the steps to authenticate your domain

## Add SendGrid Credentials to GitHub Secrets

1. Go to your GitHub repository
2. Navigate to "Settings" > "Secrets and variables" > "Actions"
3. Add the following secrets:
   - Name: `SENDGRID_API_KEY`
     - Value: The API key you copied from SendGrid
   - Name: `EMAIL_SENDER`
     - Value: The email address you want to send from (e.g., `noreply@yourdomain.com`)
   - Name: `EMAIL_RECIPIENTS`
     - Value: Comma-separated list of email addresses to receive reports (e.g., `user1@example.com,user2@example.com`)

## Verify the Configuration

1. Go to your GitHub repository
2. Navigate to "Actions"
3. Select the "Daily API Tests" workflow
4. Click "Run workflow"
5. Check "Send email report"
6. Click "Run workflow"

You should receive an email report shortly after the workflow completes.

## Troubleshooting

If you don't receive emails:

1. Check your spam/junk folder
2. Verify that the secrets are correctly set in GitHub
3. Check the GitHub Actions workflow logs for any error messages
4. Ensure your SendGrid account is active and not in a trial that has expired
