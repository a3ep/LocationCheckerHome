package net.bondar.impl

import groovy.util.logging.Log

/**
 * Builds Google Places API URL on the basis of page token.
 */
@Log
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
        log.info("Builds Google Places URL on the basis of page token")
        StringBuilder urlBuilder = new StringBuilder(super.mainUrlPart).append("pagetoken=${pageToken}").append("&key=${super.gpaKey}")
        new URL(urlBuilder.toString())
    }
}
