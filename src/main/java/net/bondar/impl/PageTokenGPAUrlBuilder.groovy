package net.bondar.impl

import groovy.util.logging.Log4j

/**
 * Builds Google Places API URL on the basis of page token.
 */
@Log4j
class PageTokenGPAUrlBuilder extends AbstractGPAUrlBuilder {
    private String pageToken

    PageTokenGPAUrlBuilder(String pageToken) {
        this.pageToken = pageToken
    }

    /**
     * Builds Google Places API URL on the basis of page token.
     *
     * @return Google Places API URL
     */
    @Override
    URL build() {
        log.info("Builds Google Places URL on the basis of page token\n")
        StringBuilder urlBuilder = new StringBuilder(super.mainUrlPart).append("pagetoken=${pageToken}").append("&key=${super.gpaKey}")
        new URL(urlBuilder.toString())
    }
}
