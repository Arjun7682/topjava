call mvn -B -s settings.xml -DskipTests=true clean package
call java -Dspring.profiles.active="datajpa,heroku" -DDATABASE_URL="postgresql://ec2-79-125-126-205.eu-west-1.compute.amazonaws.com:5432/dalpsq4q5tl3kg" -jar target/dependency/webapp-runner.jar target/*.war
