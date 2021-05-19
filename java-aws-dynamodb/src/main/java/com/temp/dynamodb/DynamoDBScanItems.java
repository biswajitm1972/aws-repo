package com.temp.dynamodb;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import java.util.Map;
import java.util.Set;


public class DynamoDBScanItems {

    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("Please specify a table name");
            System.exit(1);
        }

        String tableName = args[0];
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();

        try {

            ScanRequest scanRequest = new ScanRequest()
                    .withTableName(tableName);

            ScanResult result = client.scan(scanRequest);

            for (Map<String, AttributeValue> item : result.getItems()) {
                Set<String> keys = item.keySet();

                for (String key : keys) {

                    System.out.println ("The key name is "+key +"\n" );
                    System.out.println("The value is "+item.get(key).getS());

                }
            }


        } catch (AmazonDynamoDBException e) {
            e.getStackTrace();
        }

    }
}
