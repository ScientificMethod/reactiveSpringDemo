# Project Explanation
## Project Reactor
This project is a demo of the Spring Reactor library. A framework that greatly 
increases a Spring project's throughput by handling asynchronous actions more 
efficiently than traditional thread pools. 

The framework runs the server using just a small fixed thread-pool. It is 
able to do this because of the added requirement that blocking calls will not
be used.  

That 'small' additional expectation is the basis for an entire paradigm shift. It's also 
where the NIO acronym comes from. "New IO" back in Java 1.4, or "Non-blocking IO"
in more modern contexts (IO stands for "Input/Output", its acronyms all the way down).  

  &nbsp;

Spring traditionally has relied on the use of deep thread pools to buffer the blocking calls
found in older http (and other IO) libraries. Once a blocking call is reached the server 
jumps to the next thread, eventually coming back to the first thread to see if the wait has
completed.  
Code written in the Reactor framework instead makes use of 'callback' functions that trigger a 
function once the IO completes. This wastes fewer CPU cycles and acts as the basis for implementing
Java's functional-programming specification "Reactive Streams" as well as "**backpressure**".

  &nbsp;

Taking a step sideways into real-world use; this means programmers can easily write a program where the
consuming function (what needs the input data) is able to control the rate at
which the producing function runs (what generates or finds the input data).  
This "backpressure" lets you setup a workflow that effortlessly uses 100% of available resources, typically
CPU or database network bandwidth, without performance tests or manual balancing of
thread-pools competing for the same hardware.  

[Spring's guide for building a Reactive RESTful web service](https://spring.io/guides/gs/reactive-rest-service/)

### DynamoDB
This project stores all data in a single DynamoDB table, utilizing the "overloaded global secondary index" design pattern.  
https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/bp-gsi-overloading.html

This table also utilizes two different mechanisms for ensuring data integrity.
- Timestamp based conditionals. "Newest document wins" 
  - Ideal for saving the most 'current' record from an un-ordered at-least-once data-stream (SQS).
- Explicitly versioned records. "I am updating version 3 of this document, turning into version 4 in the process" 
  - Ideal for making guarantees about what users have changed. 

# Demo Requirements
Create an API that exposes details about a trillionaire's car collection.

Documents to be exposed:
- Textual descriptions
- Blog entries
- Inventory (location(s))
- Images
- Video
- Maintenance records

### API

- /v1/toys?id={id}
  - Exposes all documents in a single API call for a single vehicle
  - Parameters are utilized to allow flexibility for future features on a low-level URI
- /v1/toys/content/{id}/plate
  - Exposes all text, blogs, images, and video for a single vehicle
- /v1/toys/garage/{id}/plate
  - Exposes the location and maintenance records for a single vehicle
- /v1/toys/garage/{make_model}/model?year={year}
  - Exposes the location and maintenance records for a single vehicle

#### Versioning
As long as clients are written to accept unknown fields & values in a fault-tolerant way 
you can _**add**_ new fields, values, (optional) parameters, and uri's without issue.

Versioning (`/v1/`, `/v2/`, ...) URIs is a critical control-plane allowing the safest 
method for removing fields & features. Though its existence will appear unnecessary 
until you have multiple versions running at the same time.  

In support of this, it is critical that you maintain a contact mechanism for each client
to communicate any version-driven-migration needs. It could be as simple as associating 
a team's email with each approved client-authentication or requiring that each client's 
team join an email list owned by your team.


# Running the project
`./gradlew build integrationTest`  

# Local Setup
### First-Time Project Setup
#### Docker Desktop
Install docker-desktop. This is used by the integration tests to mock

&nbsp;
https://www.docker.com/products/docker-desktop/
&nbsp;  
&nbsp;

#### Local AWS
Set the region for your local machine by running the following commands.   
`touch ~/.aws/config`  
`echo "[default]" >> ~/.aws/config`  
`echo "region=us-east-1" >> ~/.aws/config`  
&nbsp;  
https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/setup-credentials.html  
&nbsp;  

### Reference Documentation

