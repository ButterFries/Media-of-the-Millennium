Sprint4.md

This document must clearly indicate the sprint goal, all stories for this sprint clearly identified, team capacity recorded, participants are recorded, decisions about user stories to be completed this sprint are clear, tasks breakdown is done.

Sprint Goal:

    Our goal this sprint was to create features that expanded upon the current features 
    in the app (ex: expand view of a media page with links) and to finish up features that
    weren't yet completed. We accomplished this through different tasks/stories that completed
    this. (Ex: Review functionality and presence within the view). Also used this sprint to update
    some views within the app for a better layout/overall look.

Stories:

User report inappropriate posts

    As a user I would like the ability to report inappropriate posts so that admins can review those posts and
    take appropriate action such as deleting the post or penalizing the user
    
    Sub Tasks:
    
        -Add button for reporting review posts
        
        -Add server endpoint for report functionality
        
        -Create database for holding reports

User account media title collections

    As a user I would like the ability to create media lists so that I can share a collection of media titles
    that I think people may be interested in
    
    Sub Tasks:
    
        -Create title collection
        
        -Share title collection
        
        -Media title collection view
        
        -Add a database table for media collections
    
User media titles suggestions section

    As a user I would like to have a section to show me suggestions based on my preferences so that I can find titles that I may like
    
    Sub Tasks:
        
        -Section for media title suggestions
        
        -Function in database to retrieve suggestions based on favorites
        
        -Endpoint to get suggestions
 
Admin delete posts

    As an admin I would like the ability to remove reported posts and penalize the poster in order to keep the 
    community clean from inappropriate posts
    
    Sub Tasks:
    
        -Create an endpoint to delete a review
        
        -Create an endpoint to get a list of reports

Links for 3rd party sites

    As an admin I would like the ability to link 3rd party sites in order to promote content related to
    the media piece
    
    Sub Tasks:
        
        -Database column for media title links
        
        -Retrieve and display links in media titles page

Media title search feature

    As a user I would like the ability to search for media titles in order to more quickly find a piece of media.
    
    Sub Tasks:
    
        -Search bar or search page
        
        -Create search results page
        
        -Create the database function for searching the title
        
        -Search endpoint
    
Media title ratings functionality

    As a user, I should be able to click rate button on the media profile page and have the rating  values updated in the database

    Sub Tasks:
    
        -Endpoint to get rating information
        
        -Endpoint to add/update rating info
        
        -Add rating functionality to media profile page
        
        -Endpoint to get users rating on a specific title

(Under Jira these are tasks but can also technically be classified as User Stories)

Retrieve mediaIDs and display
  
    As a user, I would like the ability to see a visual of the media piece so I can get a better idea on the piece of media

Team Capacity: 

Avg sprint story point: 10 (Assuming entire story points taken in to account)

7 members were available

1-2 hours a day (rough maximum)

Focus factor: 10 /(8*1.5) = 0.833

Able to work about 8 days of sprint

Team Capacity = 0.833 * 7 * (10 * 8) = 466.66

Participants:

Eric Galego

William Li

Adrian Czarnecki

Brandon Melendez

Geoffrey J P, DelaVega Ruest

Hameza Abubeker

Gurashish Arneja

Decisions about User Stories:
  - Decided on user stories that helped create a nice final product with final features that we wanted included
  - Assigned stories and subtasks by who has been working with similar code in previous sprints (Ex: If someone worked more with
  the server they would get a similar task revolving around the server while someone that worked on client side would again work 
  on client side)
  

Task Breakdown:

Functionality for posting a review

    -create reviews database and java functions
    
    -post review endpoint and get reviews endpoint
    
    -client side media review

Create endpoints and database functions for fetching toprated and new media

    -create endpoints for top rated and new media
    
    -create database functions to fetch top rated and new media

Other

    -Retrieve mediaID for each home page
    -Connect retrieval of mediaID to displaying of its contents
    -Freezing screen bug
    -User profile edit ability

Spikes:

    -Some issues with fragments and transferring information between them. Used bundles in the end. Also 
    some issues with fragments and backstack, adding to backstack or popping from it
    -Again some issues with security and what should be implemented (which form of security was best)
