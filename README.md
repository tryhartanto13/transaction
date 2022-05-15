# transaction
http://localhost:8082/transaction/api/swagger-ui.html#/transaction-controller

sample get req :
{
"refNo": "cd8d8d2b-bf12-4d62-9a54-2edeb207cdad",
}

sample get res :
{
"transactionId": 2,
"srcAccountNo": "123456789",
"destNo": "0845678941",
"destName": "Mira",
"biller": "GOPAY",
"amount": 10000,
"fee": 1000,
"refNo": "cd8d8d2b-bf12-4d62-9a54-2edeb207cdad",
"transactionStatus": 1
}

sample store req :
{
"amount": 10000,
"biller": "GOPAY",
"destName": "LALA",
"destNo": "45645616",
"fee": 1000,
"refNo": "5sa6d45s6a",
"srcAccountNo": "156489",
"transactionStatus": 1
}
