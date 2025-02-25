# API测试脚本

## 概述
本文档包含了JIRA插件的API测试脚本，用于测试各个接口的功能。

## 环境配置
```bash
# 设置基础URL和测试数据
BASE_URL="http://localhost:2990/jira"
PROJECT_KEY="TEST"
TEST_CYCLE="Cycle1"
COOKIE="JSESSIONID=your_session_id"

# 通用请求头
HEADERS=(
  -H "Content-Type: application/json;charset=UTF-8"
  -H "Cookie: ${COOKIE}"
  -H "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36"
  -H "X-Requested-With: XMLHttpRequest"
  -H "Origin: ${BASE_URL}"
  -H "Referer: ${BASE_URL}/secure/Tests.jspa"
)
```

## 测试脚本
```bash
#!/bin/bash

echo "Starting API Tests..."

# 1. 测试周期相关API测试
echo "=== Test Cycle API Tests ==="

## 1.1 获取所有测试周期
curl -X GET "${BASE_URL}/plugins/servlet/testcycles?projectKey=${PROJECT_KEY}" \
  "${HEADERS[@]}"

## 1.2 创建新测试周期
curl -X POST "${BASE_URL}/plugins/servlet/submit" \
  "${HEADERS[@]}" \
  -d "{\"testCycle\":\"NewCycle-$(date +%s)\",\"projectKey\":\"${PROJECT_KEY}\"}"

## 1.3 使用无效项目密钥
curl -X GET "${BASE_URL}/plugins/servlet/testcycles?projectKey=INVALID" \
  "${HEADERS[@]}"

# 2. 文件上传测试
echo "=== File Upload Tests ==="

## 2.1 上传CSV文件
curl -X POST "${BASE_URL}/plugins/servlet/submit" \
  "${HEADERS[@]}" \
  -H "Content-Type: multipart/form-data" \
  -F "projectKey=${PROJECT_KEY}" \
  -F "testCycle=${TEST_CYCLE}" \
  -F "file=@test-results.csv" \
  -F "attachment=on"

## 2.2 上传JSON文件
curl -X POST "${BASE_URL}/plugins/servlet/submit" \
  "${HEADERS[@]}" \
  -H "Content-Type: multipart/form-data" \
  -F "projectKey=${PROJECT_KEY}" \
  -F "testCycle=${TEST_CYCLE}" \
  -F "file=@test-results.json"

## 2.3 上传ZIP文件
curl -X POST "${BASE_URL}/plugins/servlet/submit" \
  "${HEADERS[@]}" \
  -H "Content-Type: multipart/form-data" \
  -F "projectKey=${PROJECT_KEY}" \
  -F "testCycle=${TEST_CYCLE}" \
  -F "file=@results.zip" \
  -F "attachment=on"

## 2.4 Dry Run模式测试
curl -X POST "${BASE_URL}/plugins/servlet/submit" \
  "${HEADERS[@]}" \
  -H "Content-Type: multipart/form-data" \
  -F "projectKey=${PROJECT_KEY}" \
  -F "testCycle=${TEST_CYCLE}" \
  -F "file=@test-results.csv" \
  -F "dryRun=on"

# 3. 测试用例API测试
echo "=== Test Case API Tests ==="

## 3.1 获取项目测试用例
curl -X GET "${BASE_URL}/plugins/servlet/testcases?projectKey=${PROJECT_KEY}" \
  "${HEADERS[@]}"

## 3.2 创建测试用例结果
curl -X POST "${BASE_URL}/plugins/servlet/testcases" \
  "${HEADERS[@]}" \
  -d '{
    "projectKey": "'${PROJECT_KEY}'",
    "testCaseKey": "TEST-1",
    "status": "Pass",
    "comment": "Automated test execution"
  }'

# 4. 特殊场景测试
echo "=== Special Cases Tests ==="

## 4.1 无权限访问
curl -X GET "${BASE_URL}/plugins/servlet/testcycles?projectKey=${PROJECT_KEY}" \
  -H "Cookie: invalid_session"

## 4.2 上传空文件
curl -X POST "${BASE_URL}/plugins/servlet/submit" \
  "${HEADERS[@]}" \
  -H "Content-Type: multipart/form-data" \
  -F "projectKey=${PROJECT_KEY}" \
  -F "testCycle=${TEST_CYCLE}" \
  -F "file=@empty.csv"

## 4.3 上传超大文件
dd if=/dev/zero of=large_file.csv bs=1M count=11
curl -X POST "${BASE_URL}/plugins/servlet/submit" \
  "${HEADERS[@]}" \
  -H "Content-Type: multipart/form-data" \
  -F "projectKey=${PROJECT_KEY}" \
  -F "testCycle=${TEST_CYCLE}" \
  -F "file=@large_file.csv"
rm large_file.csv

## 4.4 并发请求测试
for i in {1..5}; do
  curl -X GET "${BASE_URL}/plugins/servlet/testcases?projectKey=${PROJECT_KEY}" \
    "${HEADERS[@]}" &
done

## 4.5 测试不同状态值
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

## 测试数据

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

### 3. results.zip结构
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

## 使用说明
```bash
# 1. 创建测试数据
mkdir -p test_data/screenshots test_data/logs
cp test-results.csv test_data/
cp test-results.json test_data/
touch test_data/logs/debug.log
zip -r results.zip test_data/

# 2. 运行测试
./test-api.sh

# 3. 查看结果
./test-api.sh > test_results.log 2>&1
```

## 测试覆盖范围
- 不同文件格式上传(CSV/JSON/ZIP)
- 各种状态值测试
- 错误处理
- 并发请求
- 权限验证
- 文件大小限制
- Dry Run功能
``` 