#!/bin/bash
ansible-playbook playbooks/tar.yaml
ansible-playbook playbooks/kill.yaml
ansible-playbook playbooks/run.yaml
