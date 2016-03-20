# -*- mode: ruby -*-
# vi: set ft=ruby :

# Vagrantfile API/syntax version. Don't touch unless you know what you're doing!
VAGRANTFILE_API_VERSION = "2"
  
Vagrant.configure(VAGRANTFILE_API_VERSION) do |config|
  config.vm.box = "hashicorp/precise64"

  # Port Forwarding:
  config.vm.network "forwarded_port", guest: 8080, host: 8080
  config.vm.network :forwarded_port, guest: 35729, host: 35729



  # Synced folder
  config.vm.synced_folder "./frontend", "/vagrant"

  # Ansible provisioning
  config.vm.provision "ansible" do |ansible|
    ansible.playbook = "frontend/playbook.yml"
  end
end
