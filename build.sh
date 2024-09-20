echo $PWD
# cp $PWD/target/*.jar /home/fuentes/target
cd /home/fuentes/mcsv-training
echo $PWD
docker build -t training:1.0.1 .