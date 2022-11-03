# !/bin/bash
RUNNING_APPLICATION=$(docker ps | grep blue)
DEFAULT_CONF="nginx/default.conf"

if [ -z "$RUNNING_APPLICATION"  ];then
	echo "green Deploy..."
	docker compose build green
	docker compose up -d green
	
	while [ 1 == 1 ]; do
		echo "green health check...."
		REQUEST=$(docker exec nginx curl http://green:8080)
		echo $REQUEST
		if [ -n "$REQUEST" ]; then
			break ;
		fi
		sleep 3
	done;
	
	sed -i 's/blue/green/g' $DEFAULT_CONF
	docker exec nginx service nginx reload
	docker compose stop blue
else
	echo "blue Deploy..."
	docker compose build blue
  docker compose up -d blue
	
	while [ 1 == 1 ]; do
		echo "blue health check...."
                REQUEST=$(docker exec nginx curl http://blue:8080)
                echo $REQUEST
		if [ -n "$REQUEST" ]; then
            break ;
        fi
		sleep 3
    done;
	
	sed -i 's/green/blue/g' $DEFAULT_CONF
  docker exec nginx service nginx reload
	docker compose stop green
fi