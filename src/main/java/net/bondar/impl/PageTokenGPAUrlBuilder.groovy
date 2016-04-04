package net.bondar.impl
/**
 *
 */
class PageTokenGPAUrlBuilder extends GPAUrlBuilder {
    private String pageToken

    PageTokenGPAUrlBuilder(String pageToken) {
        this.pageToken = pageToken
    }
    /**
     *
     * @return
     */
    @Override
    URL build() {
        StringBuilder urlBuilder = new StringBuilder(super.mainUrlPart).append("pagetoken=${pageToken}").append("&key=${super.gpaKey}")
        new URL(urlBuilder.toString())
    }
}
