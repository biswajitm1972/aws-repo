package com.temp.dynamodb;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Get an item from a DynamoDB table.
 *
 */
public class GetItem
{
    public static void main(String[] args)
    {
        String table_name = args[0];
        String name = args[1];
        String projection_expression = null;

        // if a projection expression was included, set it.
        if (args.length == 3) {
            projection_expression = args[2];
        }

        System.out.format("Retrieving item \"%s\" from \"%s\"\n",
                name, table_name);

        HashMap<String,AttributeValue> key_to_get =
            new HashMap<String,AttributeValue>();

        key_to_get.put("DATABASE_NAME", new AttributeValue(name));

        GetItemRequest request = null;
        if (projection_expression != null) {
            request = new GetItemRequest()
                .withKey(key_to_get)
                .withTableName(table_name)
                .withProjectionExpression(projection_expression);
        } else {
            request = new GetItemRequest()
                .withKey(key_to_get)
                .withTableName(table_name);
        }

        final AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.defaultClient();

        try {
            Map<String,AttributeValue> returned_item =
               ddb.getItem(request).getItem();
            if (returned_item != null) {
                Set<String> keys = returned_item.keySet();
                for (String key : keys) {
                    System.out.format("%s: %s\n",
                            key, returned_item.get(key).toString());
                }
            } else {
                System.out.format("No item found with the key %s!\n", name);
            }
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
    }
}
