#!/usr/bin/env bash
## test
set -e
# -a REGISTRY_IP 镜像仓库的IP
REGISTRY_IP=192.168.174.129
A_IP=192.168.174.130
B_IP=192.168.174.131
while getopts ":a:b:c:d:" optname
do
   case "$optname" in
     "a")
       REGISTRY_IP=$OPTARG
       echo "The REGISTRY ip is $OPTARG";;
     "b")
       A_IP=$OPTARG
       echo "A ip is $OPTARG";;
     "c")
       B_IP=$OPTARG
       echo "B ip is $OPTARG";;
     ":")
       echo "No argument value for option $OPTARG"
       exit 1;;
     "?")
       echo "Unknown option $OPTARG"
       exit 1;;
     *)
       echo "Unknown error while processing options"
       exit 1;;
   esac
done

echo "start package important file system ..."

echo "end package important file system ..."

echo "start build important file system image ..."

echo "end build important file system image ..."

echo "start the important file system in  A machine , machine IP is${A_IP} ..."
ssh root@${A_IP} > /dev/null 2>&1 << eeooff
mkdir -p /root/importantFileSystem
exit
eeooff
scp .tar root@${A_IP}:/root/importantFileSystem/
#装载
ssh root@${A_IP} > /dev/null 2>&1 << eeooff
mkdir -p /etc/docker/certs.d/${domain}
exit
eeooff
echo "the important file system deploy success"


scp ${domain}.cert root@${A_IP}:/etc/docker/certs.d/${domain}/
scp ${domain}.key root@${A_IP}:/etc/docker/certs.d/${domain}/
scp ca.crt root@${A_IP}:/etc/docker/certs.d/${domain}/
scp ${domain}.crt root@${A_IP}:/etc/pki/ca-trust/source/anchors/${domain}.crt

firewall-cmd --zone=public --add-port=443/tcp --permanent
firewall-cmd --zone=public --add-port=4443/tcp --permanent
firewall-cmd --zone=public --add-port=80/tcp --permanent
firewall-cmd --reload
cat > harbor.yml <<EOF

EOF


