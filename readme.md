# Treasure Hunt API

The Peruvian government has just authorized treasure hunters to explore the 85,182 km² of the Madre de Dios department. This API will track the movements and treasure collections made by the adventurers.


## Endpoints

### Play

- **URL**: `/api/play`
- **Method**: `POST`
- **Description**: This endpoint allows the user to submit a description of the treasure hunt
- **Headers :**
  * Content-Type : `text/plain`
  * Accept : `*/*`
- **Params**: none
- **Data example** : Case and space insensitive.
    * Content:
  ```text
  C - 30 - 30
  M - 1 - 0
  M - 2 - 1
  T - 0 - 3 - 2
  T - 1 - 3 - 3
  A - Lara - 1 - 1 - S - AADADAGGAA
  A- Alex- 2-2-E-AAAAAAAAAAGAAAAAAAAAAAADAAAAAAAAAAAAA
  ```
    
- **Success Response**:
  * Code: `200 OK`
  * Content:
  ```text
  C - 30 - 30
  M - 1 - 0
  M - 2 - 1
  T - 1 - 3 - 2
  A - Lara - 0 - 4 - S - 3
  A - Alex - 25 - 0 - E - 0
  ```
- **Error Response**:
    * Code: `400 Bad Request`
    * Content:
  ```json
  {
  "message": "Input file is not valid : [M - 2 - 1 -2]",
  "httpStatus": "BAD_REQUEST",
  "timestamp": "2023-02-08T19:49:30.8574099Z"
  }
  ```
