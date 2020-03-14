Sprint3.md

This document must clearly indicate the sprint goal, all stories for this sprint clearly identified, team capacity recorded, participants are recorded, decisions about user stories to be completed this sprint are clear, tasks breakdown is done.

Sprint Goal:

    Our main goal was to create functionality between the database and app so that
    items stored in the server can appear in the app (text, images, etc.)
    We accomplished this through different tasks/stories that created this type of functionaility
    (Ex: Favourites section that retrieved and added to database as the client added or removed
    favourites)

Stories:

Favourites/Bookmarks section

    As a user I would like to have the ability to bookmark media titles so that I can save them for later

User account media title collections

    As a user I would like the ability to create media lists so that I can share a collection of media titles
    that I think people may be interested in

(Under Jira these are tasks but can also technically be classified as User Stories)

Handlers to transfer images

    As a user I would like to see a visual of the piece of media that is being reviewed
    or has been reviewed.
    
Post a review

    As a user I would like the ability to post a review for a media title which will be later shown on the page.

Top Rated and New media

    As a user I would like to see media by different categories such as top rated or new pieces of media

Team Capacity: 

Avg sprint story point: 5 (Assuming entire story points taken in to account)
7 members were available
1-2 hours a day (rough maximum)
Focus factor: 5 /(7*1.5) = 0.476
Able to work about 7 days of sprint

Team Capacity = 0.476 * 7 * (5 * 7) = 116.62

Participants:

Eric Galego

William Li

Adrian Czarnecki

Brandon Melendez

Geoffrey J P, DelaVega Ruest

Hameza Abubeker

Gurashish Arneja

Decisions about User Stories:
  - Decided on user stories that could help bring together the database and app so that we could mostly only focus
  on population of the app/database for next sprint, as well as any final extra implementations
  - Assigned stories and subtasks in somewhat first come first serve method as many tasks were working with similar aspects
  of the database, server, and app, there wasn't much need for a specialist in a specific task

Task Breakdown:

We had more big tasks this sprint, could in a sense classify most of these tasks as userstories

Design and create Media title databases (table for each genre or update the master table) and java functions

    -create media title child tables for each type
    
    -create functions to fetch extra info
    
    -create functions to update tags
    
    -create endpoint for fetching all info for media title


Create endpoints and database functions for fetching top rated, upcoming, and new media

    -create endpoints for retrieval of the above categories
    
    -create database functions in java to fetch the categories above using SQLite queries


Handlers to transfer images

    -batch fetching
  
    -transfer images from server to client
  
    -function to store images in the database
    
    -function to fetch images in the database and display them on screen


Post a review

    -create reviews database and java functions
    
    -post review endpoint
    
    -client side media review (user able to submit a review)
    
    
User account media title collections

    -create a title collection (ability for user/client to create)
    
    -share title collection
    
    -media title collection view
    
    -add database table for media title collections

User Stories

    -Favourites/Bookmarks section
    -User account media title collections
    -Handlers to transfer images
    -Post a review
    -Top Rated and New media
    -Admin link 3rd party sites (Partially completed)

Other

    -Upcoming media section (Uncompleted may be taken out due to time constraints)
    -Admin link 3rd party sites (Uncompleted may be taken out due to time constraints)
    -Adjust app views to be relative to screen size (Uncompleted)
    -Spam and bot prevention
    -Create endpoint to fill view of Media Profile Page
    -Add a class to hold globals
    -Add sessionID to account registration
    -Login/registration waits for a response (Do not accept input until response is given)

Spikes:

    -Many issues with images how they would be stored and retrieved and what it would cost
    (quality wise, storage wise, etc.) Did some research and ended up using blobs
    -Issues with security and what should be implemented (which form of security was best for
    spam/bots
