source /opt/workshops/elastic-retry.sh
export $(curl http://kubernetes-vm:9000/env | xargs)

/opt/workshops/elastic-view.sh -v oblt

hide_announcements() {
   printf "$FUNCNAME...\n"

   output=$(curl -s -X POST "$KIBANA_URL/internal/kibana/global_settings" \
      -w "\n%{http_code}" \
      -H 'kbn-xsrf: true' \
      -H 'x-elastic-internal-origin: Kibana' \
      -H "Authorization: Basic $ELASTICSEARCH_AUTH_BASE64" \
      -H 'Content-Type: application/json' \
      -d '{"changes":{"hideAnnouncements":true}}')

   # Extract HTTP status code
   http_code=$(echo "$output" | tail -n1)
   http_response=$(echo "$output" | sed '$d')
   if [ "$http_code" != "200" ]; then
      printf "$FUNCNAME...ERROR $http_code: $http_response\n"
      return 1
   fi
   printf "$FUNCNAME...SUCCESS\n"
   return 0
}
retry_command_lin hide_announcements
