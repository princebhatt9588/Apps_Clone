![repository-open-graph-template](https://user-images.githubusercontent.com/40394063/182028559-6e115af2-e1c4-4fd3-afa0-b2e10501a66f.png)

# Fakegram

# Getting started
## Prerequisites
* Git
* Docker
* docker-compose
##  Usage
0. Clone this repository
1. setup the .env file on project root directory
   ```
    # Example
    JWT_ACCESS_SECRET=ed5ef268290148a8c3e172a64aecb0f7226616e34679eb833d28c37e49aa4b88
    JWT_REFRESH_SECRET=d155c5b5fcd58362a046e5c65f0714ef897c9aca49116ec64ea9c746d2995e14
    DATABASE_USER=databaseUser
    DATABASE_PASS=databasePass
   ```
2. Run the database with `$ docker-compose up`
3. Run the spring boot with `$ gradle bootrun`

# Features

- [x] Account CRUD - Look up the account, Sign-up, Update the account, Delete the account
- [x] Auth - Sign-in, Refresh, Sign-out
- [x] Follow, Unfollow
- [x] File upload - Profile picture
- [ ] Feed Upload - Upload multiple image
- [ ] Comment
- [ ] Social sign-in

# Contribution

Report issues or open pull requests with improvements  
