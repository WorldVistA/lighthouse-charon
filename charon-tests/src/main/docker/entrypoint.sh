#!/usr/bin/env bash
set -euo pipefail
# =~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=

init() {
  if [ -z "${SENTINEL_BASE_DIR:-}" ]; then SENTINEL_BASE_DIR=/sentinel; fi
  cd $SENTINEL_BASE_DIR

  # Use the defaults unless the deployment configs are set
  if [ -n "${DEPLOYMENT_ENVIRONMENT:-}" ]; then SENTINEL_ENV="${DEPLOYMENT_ENVIRONMENT}"; fi

  if [ -n "${DEPLOYMENT_TEST_HOST:-}" ]
  then
    test -n "${DEPLOYMENT_TEST_PROTOCOL}"
    CHARON_URL="${DEPLOYMENT_TEST_PROTOCOL}://${DEPLOYMENT_TEST_HOST}"
  fi

  if [ -n "${DEPLOYMENT_TEST_PORT:-}" ]; then CHARON_PORT=${DEPLOYMENT_TEST_PORT}; fi

  if [ -z "${VISTA_AVAILABLE:-}" ]; then VISTA_AVAILABLE="true"; fi

  test -n "${SENTINEL_ENV}"
  SYSTEM_PROPERTIES=("-Dsentinel=${SENTINEL_ENV}")
}

main() {
  if [ -n "${CHARON_URL}" ]; then addToSystemProperties "sentinel.charon.url" "${CHARON_URL}"; fi
  if [ -n "${CHARON_PORT}" ]; then addToSystemProperties "sentinel.charon.port" "${CHARON_PORT}"; fi
  addToSystemProperties "client-key" "${CLIENT_KEY}"
  addToSystemProperties "vista.standard-user.access-code" "${VISTA_STANDARD_USER_ACCESS_CODE:-${VISTA_ACCESS_CODE}}"
  addToSystemProperties "vista.standard-user.verify-code" "${VISTA_STANDARD_USER_VERIFY_CODE:-${VISTA_VERIFY_CODE}}"
  addToSystemProperties "vista.is-available" "${VISTA_AVAILABLE}"

  java-tests \
    --module-name "charon-tests" \
    --regression-test-pattern ".*IT\$" \
    --smoke-test-pattern ".*PingIT\$" \
    ${SYSTEM_PROPERTIES[@]} \
    $@ \
    2>&1 | grep -v "WARNING: "

  exit $?
}

# =~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=

addToSystemProperties() {
  SYSTEM_PROPERTIES+=("-D$1=$2")
}

# =~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=

init
main $@
