package com.temp.dynamodb;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.AmazonServiceException;

/**
 * Update a DynamoDB table (change provisioned throughput).
 *
 */
public class UpdateTable
{
    public static void main(String[] args)
    {
        String table_name = args[0];
        Long read_capacity = Long.parseLong(args[1]);
        Long write_capacity = Long.parseLong(args[2]);

        System.out.format(
                "Updating %s with new provisioned throughput values\n",
                table_name);
        System.out.format("Read capacity : %d\n", read_capacity);
        System.out.format("Write capacity : %d\n", write_capacity);

        ProvisionedThroughput table_throughput = new ProvisionedThroughput(
              read_capacity, write_capacity);

        final AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.defaultClient();

        try {
            ddb.updateTable(table_name, table_throughput);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
        System.out.println("Done!");
    }
}
