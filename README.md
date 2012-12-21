# cfperm

When you run an Amazon Web Services (AWS) CloudFormation template, the
user that runs it needs to have permissions for each action that the
template performs. For example, if creating a load balancer, the user
needs permissions to create it and also to delete it, in case of a
rollback. If you have a large template with many resources, it will
require plenty of permissions. Rather than forcing you to give too
much permission to your user, this web service will parse your
template and present you with the necessary policies epxressed as JSON
code that you can paste into the permissions field for your IaM user.

## Prerequisites

You will need [Leiningen][1] 1.7.0 or above installed.

[1]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein ring server

## License

Copyright © 2012 Ulrik Sandberg
