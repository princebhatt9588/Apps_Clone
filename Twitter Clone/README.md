
![Screen-Shot-2019-07-22-at-9-52-27-AM png img fullhd medium](https://github.com/princebhatt9588/Apps_Clone/assets/117750531/e9a7b1a9-1d7f-46f9-8067-e43c793c5f4f)

**Twitter API: Managing User Data and Tweets**

In this project, you will work with a database file named `twitterClone.db`, which contains five tables: `user`, `follower`, `tweet`, `reply`, and `like`. Each table has specific columns as follows:

** User Table **

| Column   | Type    |
| -------- | ------- |
| user_id  | INTEGER |
| name     | TEXT    |
| username | TEXT    |
| password | TEXT    |
| gender   | TEXT    |

**Follower Table**

| Column              | Type    |
| ------------------- | ------- |
| follower_id         | INTEGER |
| follower_user_id    | INTEGER |
| following_user_id   | INTEGER |

**Tweet Table**

| Column    | Type     |
| --------- | -------- |
| tweet_id  | INTEGER  |
| tweet     | TEXT     |
| user_id   | INTEGER  |
| date_time | DATETIME |

**Reply Table**

| Column    | Type     |
| --------- | -------- |
| reply_id  | INTEGER  |
| tweet_id  | INTEGER  |
| reply     | TEXT     |
| user_id   | INTEGER  |
| date_time | DATETIME |

**Like Table**

| Column    | Type     |
| --------- | -------- |
| like_id   | INTEGER  |
| tweet_id  | INTEGER  |
| user_id   | INTEGER  |
| date_time | DATETIME |

## API Documentation

### API 1: Register New User

- **Path**: `/register/`
- **Method**: `POST`

**Request**

```
{
  "username": "adam_richard",
  "password": "richard_567",
  "name": "Adam Richard",
  "gender": "male"
}
```

**Responses**

- **Scenario 1**: If the username already exists
  - **Status code**: 400
  - **Body**: User already exists

- **Scenario 2**: If the password is less than 6 characters
  - **Status code**: 400
  - **Body**: Password is too short

- **Scenario 3**: Successful registration
  - **Status code**: 200
  - **Body**: User created successfully

### API 2: User Login

- **Path**: `/login/`
- **Method**: `POST`

**Request**

```
{
  "username":"JohnDoe",
  "password":"doe@123"
}
```

**Responses**

- **Scenario 1**: If the user doesn't have a Twitter account
  - **Status code**: 400
  - **Body**: Invalid user

- **Scenario 2**: If the user provides an incorrect password
  - **Status code**: 400
  - **Body**: Invalid password

- **Scenario 3**: Successful login
  - **Status code**: 200
  - **Body**: Return the JWT Token

### Authentication with JWT Token

- Verify the provided JWT token.

**Scenario 1**: If the JWT token is not provided or is invalid
- **Status code**: 401
- **Body**: Invalid JWT Token

**Scenario 2**: Proceed to the next middleware or handler after successful token verification.

### API 3: Get Latest Tweets Feed

- **Path**: `/user/tweets/feed/`
- **Method**: `GET`
- **Description**: Returns the latest tweets of people whom the user follows. Return 4 tweets at a time.
- **Response**:

```
[
   {
      "username": "SrBachchan",
      "tweet": "T 3859 - do something wonderful, people may imitate it ..",
      "dateTime": "2021-04-07 14:50:19"
   },
   ...
]
```

### API 4: Get Following List

- **Path**: `/user/following/`
- **Method**: `GET`
- **Description**: Returns the list of all names of people whom the user follows.
- **Response**:

```
[
  {
    "name": "Narendra Modi"
  },
  ...
]
```

### API 5: Get Followers List

- **Path**: `/user/followers/`
- **Method**: `GET`
- **Description**: Returns the list of all names of people who follow the user.
- **Response**:

```
[
  {
    "name": "Narendra Modi"
  },
  ...
]
```

### API 6: Get Tweet Details

- **Path**: `/tweets/:tweetId/`
- **Method**: `GET`

**Scenario 1**: If the user requests a tweet of other users he is not following
- **Status code**: 401
- **Body**: Invalid Request

**Scenario 2**: If the user requests a tweet of a user he is following
- **Response**:

```
{
   "tweet": "T 3859 - do something wonderful, people may imitate it ..",
   "likes": 3,
   "replies": 1,
   "dateTime": "2021-04-07 14:50:19"
}
```

### API 7: Get Tweet Likes

- **Path**: `/tweets/:tweetId/likes/`
- **Method**: `GET`

**Scenario 1**: If the user requests tweet likes of other users he is not following
- **Status code**: 401
- **Body**: Invalid Request

**Scenario 2**: If the user requests tweet likes of a user he is following
- **Response**:

```
{
   "likes": ["albert", ...]
}
```

### API 8: Get Tweet Replies

- **Path**: `/tweets/:tweetId/replies/`
- **Method**: `GET`

**Scenario 1**: If the user requests tweet replies of other users he is not following
- **Status code**: 401
- **Body**: Invalid Request

**Scenario 2**: If the user requests tweet replies of a user he is following
- **Response**:

```
{
   "replies": [
     {
       "name": "Narendra Modi",
       "reply": "When you see it.."
      },
      ...
   ]
}
```

### API 9: Get User Tweets

- **Path**: `/user/tweets/`
- **Method**: `GET`
- **Description**: Returns a list of all tweets of the user.
- **Response

**:

```
[
  {
    "tweet": "Ready to don the Blue and Gold",
    "likes": 3,
    "replies": 4,
    "dateTime": "2021-4-3 08:32:44"
  },
  ...
]
```

### API 10: Create a Tweet

- **Path**: `/user/tweets/`
- **Method**: `POST`
- **Description**: Create a tweet in the tweet table.
- **Request**:

```
{
   "tweet": "The Mornings..."
}
```

**Response**:

```
Created a Tweet
```

### API 11: Delete a Tweet

- **Path**: `/tweets/:tweetId/`
- **Method**: `DELETE`

**Scenario 1**: If the user requests to delete a tweet of other users
- **Status code**: 401
- **Body**: Invalid Request

**Scenario 2**: If the user deletes his tweet
- **Response**: Tweet Removed

Use `npm install` to install the required packages. Export the Express instance using the default export syntax and use Common JS module syntax.
