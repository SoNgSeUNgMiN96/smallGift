# !/bin/bash
RUNNING_APPLICATION=$(docker ps | grep blue)
DEFAULT_CONF="/home/ec2-user/app/zip/nginx/service-url.inc"

if [ -n "$RUNNING_APPLICATION"  ];then
	echo "green Deploy..."
	docker-compose build green
	docker-compose up -d green

	while [ 1 == 1 ]; do
		echo "green health check...."
		REQUEST=$(docker exec nginx curl http://13.209.78.85:8082)
		echo $REQUEST
		if [ -n "$REQUEST" ]; then
			break ;
		fi
		sleep 3
	done;

	echo "set \$service_url http://13.209.78.85:8082;" | sudo tee $DEFAULT_CONF
	docker exec nginx nginx -s reload
	docker-compose stop blue
else
	echo "blue Deploy..."
	echo "> 전환할 Port: $RUNNING_APPLICATION"
	docker-compose build blue
	docker-compose up -d blue

	while [ 1 == 1 ]; do
		echo "blue health check...."
                REQUEST=$(docker exec nginx curl http://13.209.78.85:8081)
                echo $REQUEST
		if [ -n "$REQUEST" ]; then
            break ;
        fi
		sleep 3
    done;

	echo "set \$service_url http://13.209.78.85:8081;" | sudo tee $DEFAULT_CONF
	docker exec nginx nginx -s reload
	docker-compose stop green
fi