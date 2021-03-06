package com.temp.rds;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.rds.RdsClient;
import software.amazon.awssdk.services.rds.model.DescribeDbInstancesRequest;
import software.amazon.awssdk.services.rds.model.CreateDbInstanceRequest;
import software.amazon.awssdk.services.rds.model.CreateDbInstanceResponse;
import software.amazon.awssdk.services.rds.model.RdsException;
import software.amazon.awssdk.services.rds.model.DescribeDbInstancesResponse;
import software.amazon.awssdk.services.rds.model.DBInstance;
import java.util.List;

public class CreateDBInstance {
        public static long sleepTime = 20;

        public static void main(String[] args) {

            String dbInstanceIdentifier = args[0];
            String dbName = args[1];
            String masterUsername = args[2];
            String masterUserPassword = args[3];

            Region region = Region.US_WEST_2;
            RdsClient rdsClient = RdsClient.builder()
                    .region(region)
                    .build();

            createDatabaseInstance(rdsClient, dbInstanceIdentifier, dbName, masterUsername, masterUserPassword) ;
            waitForInstanceReady(rdsClient, dbInstanceIdentifier) ;
            rdsClient.close();
        }

   
        public static void createDatabaseInstance(RdsClient rdsClient,
                                                  String dbInstanceIdentifier,
                                                  String dbName,
                                                  String masterUsername,
                                                  String masterUserPassword) {

            try {
                CreateDbInstanceRequest instanceRequest = CreateDbInstanceRequest.builder()
                        .dbInstanceIdentifier(dbInstanceIdentifier)
                        .allocatedStorage(100)
                        .dbName(dbName)
                        .engine("mysql")
                        .dbInstanceClass("db.m4.large")
                        .engineVersion("8.0.15")
                        .storageType("standard")
                        .masterUsername(masterUsername)
                        .masterUserPassword(masterUserPassword)
                        .build();

                CreateDbInstanceResponse response = rdsClient.createDBInstance(instanceRequest);
                System.out.print("The status is " + response.dbInstance().dbInstanceStatus());

            } catch (RdsException e) {
                System.out.println(e.getLocalizedMessage());
                System.exit(1);
            }
        }

    // Wait for database instance is available
    public static void waitForInstanceReady(RdsClient rdsClient, String dbInstanceIdentifier) {

        Boolean instanceReady = false;
        String instanceReadyStr = "";
        System.out.println("Waiting for instance to become available.");

        try {
            DescribeDbInstancesRequest instanceRequest = DescribeDbInstancesRequest.builder()
            .dbInstanceIdentifier(dbInstanceIdentifier)
                    .build();

        
            while (!instanceReady) {

                DescribeDbInstancesResponse response = rdsClient.describeDBInstances(instanceRequest);
                List<DBInstance> instanceList = response.dbInstances();

                for (DBInstance instance : instanceList) {

                    instanceReadyStr = instance.dbInstanceStatus();
                    if (instanceReadyStr.contains("available"))
                        instanceReady = true;
                    else {
                        System.out.print(".");
                        Thread.sleep(sleepTime * 1000);
                    }
                }
            }
            System.out.println("Database instance is available!");

        } catch (RdsException | InterruptedException e) {

            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
  }

