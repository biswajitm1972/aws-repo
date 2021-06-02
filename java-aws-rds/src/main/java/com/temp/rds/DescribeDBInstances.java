package com.temp.rds;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.rds.RdsClient;
import software.amazon.awssdk.services.rds.model.DescribeDbInstancesResponse;
import software.amazon.awssdk.services.rds.model.DBInstance;
import software.amazon.awssdk.services.rds.model.RdsException;
import java.util.List;


public class DescribeDBInstances {

    public static void main(String[] args) {

        Region region = Region.US_WEST_2;
        RdsClient rdsClient = RdsClient.builder()
                .region(region)
                .build();

        describeInstances(rdsClient) ;
        rdsClient.close();
    }

    public static void describeInstances(RdsClient rdsClient) {

        try {

            DescribeDbInstancesResponse response = rdsClient.describeDBInstances();

            List<DBInstance> instanceList = response.dbInstances();

            for (DBInstance instance: instanceList) {
                System.out.println("Instance Identifier is: "+instance.dbInstanceIdentifier());
                System.out.println("The Engine is " +instance.engine());
                System.out.println("Connection endpoint is" +instance.endpoint().address());
            }

        } catch (RdsException e) {
            System.out.println(e.getLocalizedMessage());
            System.exit(1);
        }
    }
}

