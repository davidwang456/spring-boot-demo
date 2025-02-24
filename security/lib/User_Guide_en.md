# JIRA Plugin User Guide

## 1. Installation Guide

### 1.1 System Requirements
- JIRA 8.0 or higher
- Java 8 or higher
- Supported browsers:
  - Chrome 90+
  - Firefox 80+
  - Edge 90+

### 1.2 Installation Steps
1. Download the plugin JAR package
2. Log in to JIRA Admin Console
3. Navigate to "Manage Apps" page
4. Click "Upload App"
5. Select the downloaded JAR package
6. Wait for installation to complete

## 2. Feature Instructions

### 2.1 File Upload
#### Basic Operation Process
1. Select "Test results uploader" from the top navigation bar
2. On the upload page:
   - Select project
   - Select test cycle
   - Upload test result file
   - Choose whether to upload attachments
3. Click "Submit" to proceed

#### Using Dry Run Feature
1. Complete basic information
2. Select files to upload
3. Check the "Dry Run" checkbox
4. Review pre-check results
5. Uncheck and submit after confirmation

### 2.2 Test Case Query
#### Query Operations
1. Select project
2. Click "Query Test Cases" button
3. View test case list
4. Optional: Export to CSV

#### Export CSV File
1. On the test case list page
2. Click "Download CSV" button
3. Choose save location
4. Confirm download

## 3. File Format Specifications

### 3.1 Supported File Formats
- CSV files
- JSON files
- ZIP archives

### 3.2 File Naming Convention
- Format: ProjectKey-TestCycle.extension
- Example: PROJECT-Cycle1.csv

### 3.3 File Content Requirements
- CSV Format:
  - Required columns: CASE ID, Status, Title
  - Optional columns: Description, Time, Attachment
- JSON Format:
  - Required fields: caseId, status, title
  - Optional fields: description, time, attachment

## 4. FAQ

### 4.1 Upload Issues
Q: What should I do if file upload fails?
A: Check if the file format and size meet the requirements

Q: Why isn't the test cycle loading?
A: Verify if the project selection is correct

### 4.2 Query Issues
Q: What to do when query results are empty?
A: Verify if the project selection is correct

Q: How to export data in a specific format?
A: Use the CSV export function to get the standard format

## 5. Important Notes
- File size limit is 10MB
- Ensure file names follow the convention
- Clean up temporary files regularly
- Maintain stable network connection