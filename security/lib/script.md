# API Test Scripts

## Overview
This document contains API test scripts for the JIRA plugin, used to test various interface functionalities.

## Environment Setup
```bash
# Set base URL and test data
BASE_URL="http://localhost:2990/jira"
PROJECT_KEY="TEST"
TEST_CYCLE="Cycle1"
COOKIE="JSESSIONID=your_session_id"

# Common request headers
HEADERS=(
  -H "Content-Type: application/json;charset=UTF-8"
  -H "Cookie: ${COOKIE}"
  -H "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36"
  -H "X-Requested-With: XMLHttpRequest"
  -H "Origin: ${BASE_URL}"
  -H "Referer: ${BASE_URL}/secure/Tests.jspa"
)
```

## Test Scripts
```bash
#!/bin/bash

echo "Starting API Tests..."

# 1. Test Cycle API Tests
echo "=== Test Cycle API Tests ==="

## 1.1 Get All Test Cycles
curl -X GET "${BASE_URL}/plugins/servlet/testcycles?projectKey=${PROJECT_KEY}" \
  "${HEADERS[@]}"

## 1.2 Create New Test Cycle
curl -X POST "${BASE_URL}/plugins/servlet/submit" \
  "${HEADERS[@]}" \
  -d "{\"testCycle\":\"NewCycle-$(date +%s)\",\"projectKey\":\"${PROJECT_KEY}\"}"

## 1.3 Invalid Project Key Test
curl -X GET "${BASE_URL}/plugins/servlet/testcycles?projectKey=INVALID" \
  "${HEADERS[@]}"

# 2. File Upload Tests
echo "=== File Upload Tests ==="

## 2.1 Upload CSV File
curl -X POST "${BASE_URL}/plugins/servlet/submit" \
  "${HEADERS[@]}" \
  -H "Content-Type: multipart/form-data" \
  -F "projectKey=${PROJECT_KEY}" \
  -F "testCycle=${TEST_CYCLE}" \
  -F "file=@test-results.csv" \
  -F "attachment=on"

## 2.2 Upload JSON File
curl -X POST "${BASE_URL}/plugins/servlet/submit" \
  "${HEADERS[@]}" \
  -H "Content-Type: multipart/form-data" \
  -F "projectKey=${PROJECT_KEY}" \
  -F "testCycle=${TEST_CYCLE}" \
  -F "file=@test-results.json"

## 2.3 Upload ZIP File
curl -X POST "${BASE_URL}/plugins/servlet/submit" \
  "${HEADERS[@]}" \
  -H "Content-Type: multipart/form-data" \
  -F "projectKey=${PROJECT_KEY}" \
  -F "testCycle=${TEST_CYCLE}" \
  -F "file=@results.zip" \
  -F "attachment=on"

## 2.4 Dry Run Mode Test
curl -X POST "${BASE_URL}/plugins/servlet/submit" \
  "${HEADERS[@]}" \
  -H "Content-Type: multipart/form-data" \
  -F "projectKey=${PROJECT_KEY}" \
  -F "testCycle=${TEST_CYCLE}" \
  -F "file=@test-results.csv" \
  -F "dryRun=on"

# 3. Test Case API Tests
echo "=== Test Case API Tests ==="

## 3.1 Get Project Test Cases
curl -X GET "${BASE_URL}/plugins/servlet/testcases?projectKey=${PROJECT_KEY}" \
  "${HEADERS[@]}"

## 3.2 Create Test Case Result
curl -X POST "${BASE_URL}/plugins/servlet/testcases" \
  "${HEADERS[@]}" \
  -d '{
    "projectKey": "'${PROJECT_KEY}'",
    "testCaseKey": "TEST-1",
    "status": "Pass",
    "comment": "Automated test execution"
  }'

# 4. Special Case Tests
echo "=== Special Cases Tests ==="

## 4.1 Unauthorized Access
curl -X GET "${BASE_URL}/plugins/servlet/testcycles?projectKey=${PROJECT_KEY}" \
  -H "Cookie: invalid_session"

## 4.2 Empty File Upload
curl -X POST "${BASE_URL}/plugins/servlet/submit" \
  "${HEADERS[@]}" \
  -H "Content-Type: multipart/form-data" \
  -F "projectKey=${PROJECT_KEY}" \
  -F "testCycle=${TEST_CYCLE}" \
  -F "file=@empty.csv"

## 4.3 Large File Upload
dd if=/dev/zero of=large_file.csv bs=1M count=11
curl -X POST "${BASE_URL}/plugins/servlet/submit" \
  "${HEADERS[@]}" \
  -H "Content-Type: multipart/form-data" \
  -F "projectKey=${PROJECT_KEY}" \
  -F "testCycle=${TEST_CYCLE}" \
  -F "file=@large_file.csv"
rm large_file.csv

## 4.4 Concurrent Request Test
for i in {1..5}; do
  curl -X GET "${BASE_URL}/plugins/servlet/testcases?projectKey=${PROJECT_KEY}" \
    "${HEADERS[@]}" &
done

## 4.5 Different Status Values Test
for status in "Pass" "Fail" "Blocked" "In Process" "Invalid"; do
  curl -X POST "${BASE_URL}/plugins/servlet/testcases" \
    "${HEADERS[@]}" \
    -d '{
      "projectKey": "'${PROJECT_KEY}'",
      "testCaseKey": "TEST-1",
      "status": "'$status'",
      "comment": "Testing status: '$status'"
    }'
done
```

## Test Data

### 1. test-results.csv
```csv
CASE ID,Execution Status,Title,Description,Execution Time,Attachment Name
TEST-1,Pass,Login Test,Successful login test,2024-02-25 10:00:00,screenshot1.png
TEST-2,Fail,Search Test,Failed search test,2024-02-25 11:00:00,error.log:debug.log
TEST-3,Blocked,Payment Test,Payment gateway unavailable,2024-02-25 12:00:00,
TEST-4,In Process,Profile Test,Testing profile updates,2024-02-25 13:00:00,profile.jpg
```

### 2. test-results.json
```json
{
  "elements": [
    {
      "keyword": "Scenario",
      "type": "scenario",
      "name": "Login Functionality",
      "tags": [{"name": "@CASE=TEST-1"}],
      "steps": [
        {
          "keyword": "Given",
          "name": "user is on login page",
          "result": {
            "status": "passed",
            "duration": 1000000
          }
        },
        {
          "keyword": "When",
          "name": "user enters credentials",
          "result": {
            "status": "passed",
            "duration": 2000000
          }
        }
      ]
    }
  ]
}
```

### 3. results.zip Structure
```
results.zip/
  ├── test1.csv
  ├── test2.json
  ├── screenshots/
  │   ├── login.png
  │   └── error.png
  └── logs/
      └── debug.log
```

## Usage Instructions
```bash
# 1. Create test data
mkdir -p test_data/screenshots test_data/logs
cp test-results.csv test_data/
cp test-results.json test_data/
touch test_data/logs/debug.log
zip -r results.zip test_data/

# 2. Run tests
./test-api.sh

# 3. View results
./test-api.sh > test_results.log 2>&1
```

## Test Coverage
- Different file format uploads (CSV/JSON/ZIP)
- Various status value tests
- Error handling
- Concurrent requests
- Authorization validation
- File size limits
- Dry Run functionality
``` 