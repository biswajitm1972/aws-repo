package com.temp.dynamodb;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;

import java.util.HashMap;

import com.amazonaws.AmazonServiceException;

/**
 * Query a DynamoDB table.
 */
public class Query
{
    public static void main(String[] args)
    {
        String table_name = args[0];
        String partition_key_name = args[1];
        String partition_key_val = args[2];
        String partition_alias = "#a";

        System.out.format("Querying %s", table_name);
        System.out.println("");

        final AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.defaultClient();

        //set up an alias for the partition key name in case it's a reserved word
        HashMap<String,String> attrNameAlias =
                new HashMap<String,String>();
        attrNameAlias.put(partition_alias, partition_key_name);

        //set up mapping of the partition name with the value
        HashMap<String, AttributeValue> attrValues =
                new HashMap<String,AttributeValue>();
        attrValues.put(":"+partition_key_name, new AttributeValue().withS(partition_key_val));

        QueryRequest queryReq = new QueryRequest()
        		.withTableName(table_name)
        		.withKeyConditionExpression(partition_alias + " = :" + partition_key_name)
        		.withExpressionAttributeNames(attrNameAlias)
        		.withExpressionAttributeValues(attrValues);

        try {
        	QueryResult response = ddb.query(queryReq);
        	System.out.println(response.getCount());
        } catch (AmazonDynamoDBException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
        System.out.println("Done!");
    }
}
