package persistence;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.*;

import java.util.ArrayList;
import java.util.List;

public class DynamoDBManager {

    private AmazonDynamoDB getHandle()
    {
        AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
                .build();

        return ddb;

    }

   final DynamoDBMapper mapper ;


    public DynamoDBManager() {
        final AmazonDynamoDB ddb = getHandle();
         mapper = new DynamoDBMapper(ddb);

    }


    public void createUserTable() {

        List<AttributeDefinition> definitions = new ArrayList<>();
        definitions.add(new AttributeDefinition(
                "UserId", ScalarAttributeType.S));

        KeySchemaElement keySchemaElement = new KeySchemaElement("UserId", KeyType.HASH);

        createTable("User",definitions,keySchemaElement);
    }

    public UserRecord getUser(String userId)
    {
        UserRecord userRecord = mapper.load(UserRecord.class, userId);
        return userRecord;
    }



    public void putUser(UserRecord userRecord)
    {
        mapper.save(userRecord);

    }

    public void removeUser(UserRecord userRecord)
    {
        mapper.delete(userRecord);
    }




    public void printUsers()
    {
            printAllRecords(UserRecord.class);
    }


    private  void printAllRecords(Class<?> clazz)
    {

        final AmazonDynamoDB ddb = getHandle();
        DynamoDBMapper mapper = new DynamoDBMapper(ddb);

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        var scanResult = mapper.scan(clazz, scanExpression);

        scanResult.stream().forEach(r-> System.out.println(r));


    }


    private void createTable(String tableName , List<AttributeDefinition> definitions,KeySchemaElement keySchemaElement )
    {



       /* definitions.add(new AttributeDefinition(
                "Identity", ScalarAttributeType.S)); */

        CreateTableRequest request = new CreateTableRequest()
                .withAttributeDefinitions(definitions)
                .withKeySchema(keySchemaElement)
                .withProvisionedThroughput(new ProvisionedThroughput(
                        new Long(10), new Long(10)))
                .withTableName(tableName);

        final AmazonDynamoDB ddb = getHandle();




        try {
            CreateTableResult result = ddb.createTable(request);
            System.out.println(result);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
        System.out.println("Done!");
    }


    public static void main(String[] args) {

        DynamoDBManager manager = new DynamoDBManager();

       // manager.createUserTable();

        manager.printUsers();

    }
}




