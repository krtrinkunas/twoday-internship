# twoday-internship
A homework task for internship at Twoday

The following is a Zoo Enclosure assignment program written in Java, Spring Boot and H2 database.
The zoo consists of animals and enclosures. Animals are assigned to enclosures according to the criteria written in the task.
The assignment is done automatically once animals are written to the database or an animal is updated (its amount is increased).
The assignment algorithm can be found in [AssignmentService](https://github.com/krtrinkunas/twoday-internship/blob/master/src/main/java/lt/krtrinkunas/twodayinternship/service/AssignmentService.java) class.

Since the sizes of enclosures are not given, they can be configured in [application.properties](https://github.com/krtrinkunas/twoday-internship/blob/master/src/main/resources/application.properties#L11-L14) file.

You can create enclosures by running POST http://localhost:9090/api/enclosures with the following request body structure:
```
{
    "enclosures": [
        {
            "name": "string",
            "size": "string",
            "location": "string",
            "objects": [
                "string"
            ]
        }
    ]
}
```
You can create animals by running POST http://localhost:9090/api/animals with the following request body structure
(please note that animals will not be created if there are no enclosures left):
```
{
    "animals": [
        {
            "species": "string",
            "food": "string",
            "amount": "integer"
        }
    ]
}
```
In addition, there are also CRUD operations for the animals:

POST http://localhost:9090/api/animal will create a single animal. Request body:
```
{
    "species": "string",
    "food": "string",
    "amount": "integer"
}
```
PUT http://localhost:9090/api/animals/{id} will update an animal with specified id. Request body:
```
{
    "species": "string",
    "food": "string",
    "amount": "integer"
}
```

GET http://localhost:9090/api/animals/ will return all animals.

GET http://localhost:9090/api/animals/{id} will return an animal with the specified id.

DELETE http://localhost:9090/api/animals/{id} will delete an animal with the specified id.
