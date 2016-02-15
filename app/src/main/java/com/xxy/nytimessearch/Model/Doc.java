package com.xxy.nytimessearch.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Doc {

  private String web_url;
  private String snippet;
  private String leadParagraph;
  private Object _abstract;
  private Object printPage;
  private List<Object> blog = new ArrayList<Object>();
  private String source;
  private List<Multimedium> multimedia = new ArrayList<Multimedium>();
  private Headline headline;
  private List<Object> keywords = new ArrayList<Object>();
  private String pubDate;
  private String documentType;
  private String newsDesk;
  private String sectionName;
  private Object subsectionName;
  private Object byline;
  private String typeOfMaterial;
  private String Id;
  private String wordCount;
  private String slideshowCredits;
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /**
   * @return The webUrl
   */
  public String getWebUrl() {
    return web_url;
  }

  /**
   * @param webUrl The web_url
   */
  public void setWebUrl(String webUrl) {
    this.web_url = webUrl;
  }

  /**
   * @return The snippet
   */
  public String getSnippet() {
    return snippet;
  }

  /**
   * @param snippet The snippet
   */
  public void setSnippet(String snippet) {
    this.snippet = snippet;
  }

  /**
   * @return The leadParagraph
   */
  public String getLeadParagraph() {
    return leadParagraph;
  }

  /**
   * @param leadParagraph The lead_paragraph
   */
  public void setLeadParagraph(String leadParagraph) {
    this.leadParagraph = leadParagraph;
  }

  /**
   * @return The _abstract
   */
  public Object getAbstract() {
    return _abstract;
  }

  /**
   * @param _abstract The abstract
   */
  public void setAbstract(Object _abstract) {
    this._abstract = _abstract;
  }

  /**
   * @return The printPage
   */
  public Object getPrintPage() {
    return printPage;
  }

  /**
   * @param printPage The print_page
   */
  public void setPrintPage(Object printPage) {
    this.printPage = printPage;
  }

  /**
   * @return The blog
   */
  public List<Object> getBlog() {
    return blog;
  }

  /**
   * @param blog The blog
   */
  public void setBlog(List<Object> blog) {
    this.blog = blog;
  }

  /**
   * @return The source
   */
  public String getSource() {
    return source;
  }

  /**
   * @param source The source
   */
  public void setSource(String source) {
    this.source = source;
  }

  /**
   * @return The multimedia
   */
  public List<Multimedium> getMultimedia() {
    return multimedia;
  }

  /**
   * @param multimedia The multimedia
   */
  public void setMultimedia(List<Multimedium> multimedia) {
    this.multimedia = multimedia;
  }

  /**
   * @return The headline
   */
  public Headline getHeadline() {
    return headline;
  }

  /**
   * @param headline The headline
   */
  public void setHeadline(Headline headline) {
    this.headline = headline;
  }

  /**
   * @return The keywords
   */
  public List<Object> getKeywords() {
    return keywords;
  }

  /**
   * @param keywords The keywords
   */
  public void setKeywords(List<Object> keywords) {
    this.keywords = keywords;
  }

  /**
   * @return The pubDate
   */
  public String getPubDate() {
    return pubDate;
  }

  /**
   * @param pubDate The pub_date
   */
  public void setPubDate(String pubDate) {
    this.pubDate = pubDate;
  }

  /**
   * @return The documentType
   */
  public String getDocumentType() {
    return documentType;
  }

  /**
   * @param documentType The document_type
   */
  public void setDocumentType(String documentType) {
    this.documentType = documentType;
  }

  /**
   * @return The newsDesk
   */
  public String getNewsDesk() {
    return newsDesk;
  }

  /**
   * @param newsDesk The news_desk
   */
  public void setNewsDesk(String newsDesk) {
    this.newsDesk = newsDesk;
  }

  /**
   * @return The sectionName
   */
  public String getSectionName() {
    return sectionName;
  }

  /**
   * @param sectionName The section_name
   */
  public void setSectionName(String sectionName) {
    this.sectionName = sectionName;
  }

  /**
   * @return The subsectionName
   */
  public Object getSubsectionName() {
    return subsectionName;
  }

  /**
   * @param subsectionName The subsection_name
   */
  public void setSubsectionName(Object subsectionName) {
    this.subsectionName = subsectionName;
  }

  /**
   * @return The byline
   */
  //TODO: find out why byline was not defined correctly by http://www.jsonschema2pojo.org/
  public Object getByline() {
    return byline;
  }

  /**
   * @param byline The byline
   */
  public void setByline(Object byline) {
    this.byline = byline;
  }

  /**
   * @return The typeOfMaterial
   */
  public String getTypeOfMaterial() {
    return typeOfMaterial;
  }

  /**
   * @param typeOfMaterial The type_of_material
   */
  public void setTypeOfMaterial(String typeOfMaterial) {
    this.typeOfMaterial = typeOfMaterial;
  }

  /**
   * @return The Id
   */
  public String getId() {
    return Id;
  }

  /**
   * @param Id The _id
   */
  public void setId(String Id) {
    this.Id = Id;
  }

  /**
   * @return The wordCount
   */
  public String getWordCount() {
    return wordCount;
  }

  /**
   * @param wordCount The word_count
   */
  public void setWordCount(String wordCount) {
    this.wordCount = wordCount;
  }

  /**
   * @return The slideshowCredits
   */
  public String getSlideshowCredits() {
    return slideshowCredits;
  }

  /**
   * @param slideshowCredits The slideshow_credits
   */
  public void setSlideshowCredits(String slideshowCredits) {
    this.slideshowCredits = slideshowCredits;
  }

  public Map<String, Object> getAdditionalProperties() {
    return this.additionalProperties;
  }

  public void setAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
  }

}
