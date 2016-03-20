# Frontend

## Vagrant Config

All Vagrant conf is in Vagrantfile. To install some dependencies we have used Ansible, and these dependencies are in the playbook.yml.
With Vagrant I synced the `/frontend` folder in order to execute the Grunt inside Vagrant with this code. If you change some of this code in your local machine, Vagrant will recognize it.

## How to run Vagrant and Ansible

Probably, if you do `vagrant up` Ansible run all the tasks and install the dependencies.
If not, you can do after `vagrant provision` and Ansible will install everything.
(If you don't change the playbook.yml, the next time you only need to do `vagrant up` because everything will be installed)

Once you do this, you need to access via SSH to the Vagrant machine in order to run the Grunt server.
The first time, you need to install the Compass library with Gem, I don't know why.. but Ansible install a fucked library!
To do this: `sudo gem install compass`

Finally, you need to move to `/vagrant` and run the grunt server:
`grunt serve`

Now, you can access to the AngularJS page in your local browser, at `localhost:8080`.

## Testing

Running `grunt test` will run the unit tests with karma.
