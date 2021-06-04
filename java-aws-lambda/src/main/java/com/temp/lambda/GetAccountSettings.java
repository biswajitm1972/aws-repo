package com.temp.lambda;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.GetAccountSettingsResponse;
import software.amazon.awssdk.services.lambda.model.LambdaException;

public class GetAccountSettings {

    public static void main(String[] args) {

        Region region = Region.US_EAST_1;
        LambdaClient awsLambda = LambdaClient.builder()
                .region(region)
                .build();

        getSettings(awsLambda);
        awsLambda.close();
    }

    public static void getSettings(LambdaClient awsLambda) {

        try {
            GetAccountSettingsResponse response = awsLambda.getAccountSettings();
            System.out.println("Total code size for your account is "+response.accountLimit().totalCodeSize() +" bytes");

        } catch(LambdaException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
