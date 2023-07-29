
![hero-image](https://github.com/princebhatt9588/Apps_Clone/assets/117750531/7c22ace3-3278-4c93-87ce-c2b3cc049afb)

# Instagraam

# Getting started
## Prerequisites
* Ensure you have the following installed:
  * Git
  * Docker
  * docker-compose

## Usage
1. Clone this repository.
2. Set up the `.env` file in the project's root directory as follows:
   ```
    # Example
    JWT_ACCESS_SECRET=ed5ef268290148a8c3e172a64aecb0f7226616e34679eb833d28c37e49aa4b88
    JWT_REFRESH_SECRET=d155c5b5fcd58362a046e5c65f0714ef897c9aca49116ec64ea9c746d2995e14
    DATABASE_USER=databaseUser
    DATABASE_PASS=databasePass
   ```
3. Start the database with `$ docker-compose up`.
4. Run the spring boot application with `$ gradle bootrun`.

# Features

- [x] Account CRUD - Ability to look up, sign-up, update, and delete accounts.
- [x] Authentication - Includes sign-in, refresh, and sign-out functionalities.
- [x] Follow, Unfollow - Option to follow or unfollow other users.
- [x] File Upload - Supports profile picture uploads.
- [ ] Feed Upload - Work in progress for uploading multiple images for a feed post.
- [ ] Comment - Upcoming feature to add comments on posts.
- [ ] Social Sign-in - Planned functionality for social sign-in options.
