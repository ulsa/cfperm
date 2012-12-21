#  Clojure/Compojure clickstart

Build status: [![Build Status](https://buildhive.cloudbees.com/job/CloudBees-community/job/clojure-clickstart/badge/icon)](https://buildhive.cloudbees.com/job/CloudBees-community/job/clojure-clickstart/)

This clickstart demonstrates a simple clojure with compojure app built via lein - all thanks to the <a href="http://www.meetup.com/Austin-Clojure-Meetup/">Austin Clojure group</a>

Clickstarts are quickstarts (hey that rymes!) that get you going with a private repo, build and continuous deployment. 
<a href="https://www.cloudbees.com/signup">Signup</a> to cloudbees if you haven't already. 

<a href="https://grandcentral.cloudbees.com/?CB_clickstart=https://raw.github.com/CloudBees-community/clojure-clickstart/master/clickstart.json"><img src="https://d3ko533tu1ozfq.cloudfront.net/clickstart/deployInstantly.png"/></a>

Launch this clickstart and glory could be yours too ! Use it as a building block if you like.

You can launch this on CloudBees via a clickstart automatically, or follow the instructions below. 


# Deploying manually: 

Use the lein-cloudbees plugin as per https://clojars.org/lein-cloudbees

    lein cloudbees deploy

## Prerequisites

You will need [Leiningen][1] 2 or above installed.

[1]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein ring server

## Notes

This currently runs as a war in a container - 
of course CloudBees can run plain <a href="https://developer.cloudbees.com/bin/view/RUN/Java+Container">JVM apps</a> so that is possible is desired. 


## License

Copyright © 2012 FIXME
