package com.westat

import javafx.scene.control.TreeItem
//import play.api.libs.json.Json
import scala.collection.mutable.ListBuffer

/**
 * Created by Owner on 3/14/2017.
 */

//------------- temp for static testing
case class QuestionItem(id : String, title : String, quest_wording : Option[String], item_number : Option[String],
                        item_wording : Option[String], hdr_column_ref : Option[String], hdr_keycode : Option[String],
                        responses_id : Option[String], response_label : Option[String], response_instruction : Option[String],
                        keycode : Option[String], ag_id_paper : Option[String], ag_id_electronic : Option[String],
                        ag_id_dc : Option[String], de_name : Option[String], instance_offset : Option[Float],
                        instance_index : Option[Float], question_number : Option[String], ref_period : String)
/*
object QuestionItem {
  implicit val questionItemReads = Json.reads[QuestionItem]
  implicit val questionItemWrites = Json.writes[QuestionItem]

  private val storedQItemsJson =
    """
      [{"id":"D078FF39-F25C-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_number":"A.","item_wording":"Is your company owned or controlled by another company  ","responses_id":"D078FF39-F25F-91A7-E002-F10EC8809F01","response_label":"Yes","response_instruction":"- <I>Complete lines B and C and return this form with your completed $$00 Economic Census form.</I>","keycode":"0005","ag_id_paper":"35E5C34A-9D1B-406B-AC87-D027F9E05184","ag_id_electronic":"2A291202-6FA4-0417-E040-18ACC96076A7","ag_id_dc":"1CB5B524-E5B4-73B5-E040-18ACC5601885","de_name":"AFFIL_SPL_DOM_OWN_YES","ref_period":"1997 CENSUS"},{"id":"D078FF39-F25C-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_number":"A.","item_wording":"Is your company owned or controlled by another company  ","responses_id":"D078FF39-F25E-91A7-E002-F10EC8809F01","response_label":"No","response_instruction":"- <I>Discard this form (NC-99513) and return your completed $$00 Economic Census form.</I>","keycode":"0006","ag_id_paper":"35E5C34A-9D1B-406B-AC87-D027F9E05184","ag_id_electronic":"2A291202-6FA4-0417-E040-18ACC96076A7","ag_id_dc":"1CB5B524-E5B2-73B5-E040-18ACC5601885","de_name":"AFFIL_SPL_DOM_OWN_NO","ref_period":"1997 CENSUS"},{"id":"D078FF39-F261-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_wording":"<B>OR</B>  ","ref_period":"1997 CENSUS"},{"id":"D078FF39-F264-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_wording":"does your company operate at more than one physical location?  ","ref_period":"1997 CENSUS"},{"id":"D078FF39-F267-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_number":"B.","item_wording":"Ownership or control  ","ref_period":"1997 CENSUS"},{"id":"D078FF39-F26A-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_number":"1.","item_wording":"Does another company hold more than 50 percent of the voting stock of your company <B>or</B> have the power to control the management and policies of your company?  ","responses_id":"D078FF39-F26D-91A7-E002-F10EC8809F01","response_label":"Yes","response_instruction":"- <I>Enter the following information for the owning or controlling company</I>","keycode":"0008","ag_id_paper":"35E5C34A-9D1B-406B-AC87-D027F9E05184","ag_id_electronic":"2A291202-6FA4-0417-E040-18ACC96076A7","ag_id_dc":"1CB5B524-EE2A-73B5-E040-18ACC5601885","de_name":"AFFIL_OWNER_YES","ref_period":"1997 CENSUS"},{"id":"D078FF39-F26A-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_number":"1.","item_wording":"Does another company hold more than 50 percent of the voting stock of your company <B>or</B> have the power to control the management and policies of your company?  ","responses_id":"D078FF39-F26C-91A7-E002-F10EC8809F01","response_label":"No","response_instruction":"- <I>Go to line C</I>","keycode":"0009","ag_id_paper":"35E5C34A-9D1B-406B-AC87-D027F9E05184","ag_id_electronic":"2A291202-6FA4-0417-E040-18ACC96076A7","ag_id_dc":"1CB5B524-E5B8-73B5-E040-18ACC5601885","de_name":"AFFIL_OWNER_NO","ref_period":"1997 CENSUS"},{"id":"D078FF39-F26F-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_wording":"Enter EIN of owning or controlling company <I>(9 digits)</I> ","ref_period":"1997 CENSUS"},{"id":"D078FF39-F273-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_wording":"  ","responses_id":"D078FF39-F275-91A7-E002-F10EC8809F01","keycode":"0080","ag_id_paper":"63C0BD88-3281-42AF-9370-6F3131AF5C0C","ag_id_electronic":"2FFA476D-94E4-B36C-E040-18ACC96034F5","ag_id_dc":"1CB5B524-E90A-73B5-E040-18ACC5601885","de_name":"AFFIL_OWNER_NAME","ref_period":"1997 CENSUS"},{"id":"D078FF39-F273-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_wording":"  ","responses_id":"D078FF39-F274-91A7-E002-F10EC8809F01","keycode":"0081","ag_id_paper":"EB12A4BE-72F7-48B0-B49A-6CD85597B5F7","ag_id_electronic":"2A291202-6FA3-0417-E040-18ACC96076A7","ag_id_dc":"1CB5B524-E7D8-73B5-E040-18ACC5601885","de_name":"AFFIL_OWNER_EIN","ref_period":"1997 CENSUS"},{"id":"D078FF39-F278-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_wording":"  ","responses_id":"D078FF39-F279-91A7-E002-F10EC8809F01","keycode":"0082","ag_id_paper":"63C0BD88-3281-42AF-9370-6F3131AF5C0C","ag_id_electronic":"2FFA476D-94E4-B36C-E040-18ACC96034F5","ag_id_dc":"1CB5B524-E908-73B5-E040-18ACC5601885","de_name":"AFFIL_OWNER_ADDR_STREET","ref_period":"1997 CENSUS"},{"id":"D078FF39-F27C-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_wording":"  ","responses_id":"D078FF39-F27D-91A7-E002-F10EC8809F01","keycode":"0083","ag_id_paper":"2CA684E8-8B03-4279-A924-A22B73EAEFEB","ag_id_electronic":"2FFA476D-94DD-B36C-E040-18ACC96034F5","ag_id_dc":"1CB5B524-EE26-73B5-E040-18ACC5601885","de_name":"AFFIL_OWNER_ADDR_CITY","ref_period":"1997 CENSUS"},{"id":"D078FF39-F27C-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_wording":"  ","responses_id":"D078FF39-F27E-91A7-E002-F10EC8809F01","keycode":"0084","ag_id_paper":"A5F37A59-592F-461C-A43A-C94D0F370ACD","ag_id_electronic":"2A291202-6FA8-0417-E040-18ACC96076A7","ag_id_dc":"1CB5B524-DC60-73B5-E040-18ACC5601885","de_name":"AFFIL_OWNER_ADDR_ST","ref_period":"1997 CENSUS"},{"id":"D078FF39-F27C-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_wording":"  ","responses_id":"D078FF39-F27F-91A7-E002-F10EC8809F01","keycode":"0085","ag_id_paper":"7ACC82BA-84E5-481F-AE4F-3062385EA01F","ag_id_electronic":"2A291202-6FA9-0417-E040-18ACC96076A7","ag_id_dc":"1CB5B524-EE28-73B5-E040-18ACC5601885","de_name":"AFFIL_OWNER_ADDR_ZIP","ref_period":"1997 CENSUS"},{"id":"D078FF39-F282-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_number":"2.","item_wording":"Percent of voting stock held by owning <B>or</B> controlling company <I>(Mark \"X\" only ONE box.)</I> ","responses_id":"D078FF39-F287-91A7-E002-F10EC8809F01","response_label":"Less than 50%","keycode":"0027","ag_id_paper":"35E5C34A-9D1B-406B-AC87-D027F9E05184","ag_id_electronic":"2A291202-6FA4-0417-E040-18ACC96076A7","ag_id_dc":"1CB5B524-E5BE-73B5-E040-18ACC5601885","de_name":"AFFIL_OWNER_STOCK_LT50","ref_period":"1997 CENSUS"},{"id":"D078FF39-F282-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_number":"2.","item_wording":"Percent of voting stock held by owning <B>or</B> controlling company <I>(Mark \"X\" only ONE box.)</I> ","responses_id":"D078FF39-F285-91A7-E002-F10EC8809F01","response_label":"50%","keycode":"0028","ag_id_paper":"35E5C34A-9D1B-406B-AC87-D027F9E05184","ag_id_electronic":"2A291202-6FA4-0417-E040-18ACC96076A7","ag_id_dc":"1CB5B524-E5BA-73B5-E040-18ACC5601885","de_name":"AFFIL_OWNER_STOCK_EQ50","ref_period":"1997 CENSUS"},{"id":"D078FF39-F282-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_number":"2.","item_wording":"Percent of voting stock held by owning <B>or</B> controlling company <I>(Mark \"X\" only ONE box.)</I> ","responses_id":"D078FF39-F286-91A7-E002-F10EC8809F01","response_label":"More than 50%","keycode":"0029","ag_id_paper":"35E5C34A-9D1B-406B-AC87-D027F9E05184","ag_id_electronic":"2A291202-6FA4-0417-E040-18ACC96076A7","ag_id_dc":"1CB5B524-E5BC-73B5-E040-18ACC5601885","de_name":"AFFIL_OWNER_STOCK_GT50","ref_period":"1997 CENSUS"},{"id":"D078FF39-F289-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_number":"C.","item_wording":"Number of establishments operated at the end of $$00 under the EIN shown in the mailing address or as corrected in <reverse-circle text=\"2\"/> on the first page of the $$00 Economic Census form  ","responses_id":"D078FF39-F28B-91A7-E002-F10EC8809F01","keycode":"0087","ag_id_paper":"71E3C857-D032-4EE0-8DC1-E12343D98F0E","ag_id_electronic":"30215AF2-C8C6-8C51-E040-18ACC76053E2","ag_id_dc":"1CB5B524-E7BA-73B5-E040-18ACC5601885","de_name":"AFFIL_SPL_ESTAB","ref_period":"1997 CENSUS"},{"id":"D078FF39-F28E-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_number":"60","item_wording":"  <B><I>If more than one establishment:</I></B>","ref_period":"1997 CENSUS"},{"id":"D078FF39-F291-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_number":"70","item_wording":"  <I>Provide the physical location address and other information requested on the back of this form for each location.</I>","ref_period":"1997 CENSUS"},{"id":"D078FF39-F294-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_number":"80","item_wording":"  <I>Provide the headquarter's location first, followed by all other locations.</I>","ref_period":"1997 CENSUS"},{"id":"D078FF39-F297-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_number":"90","item_wording":"  <I>Data for establishments operated during $$00, but not in operation at the end of the year, should be included with the headquarter's location.</I>","ref_period":"1997 CENSUS"},{"id":"D078FF39-F29A-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_wording":"  <I>The sum of all sales, shipments, receipts, or revenue and employment and payroll for all locations should equal the amounts reported in <reverse-circle text=\"4\"/> and <reverse-circle text=\"6\"/> of the $$00 Economic Census form.</I>","ref_period":"1997 CENSUS"},{"id":"D078FF39-F29D-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_wording":"  <I>For employees that worked at more than one location, report the employment and payroll data for the employees at the ONE location where they spent most of their working time.</I>","ref_period":"1997 CENSUS"},{"id":"D078FF39-F2A0-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_number":"C.","item_wording":"Number of establishments operated at the end of $$00 under the EIN shown in the mailing address or as corrected in <reverse-circle text=\"2\"/> on the first page of the $$00 Economic Census form - Continued  ","ref_period":"1997 CENSUS"},{"id":"D078FF39-F2A3-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_wording":"  <I><B>BEFORE YOU BEGIN:</B>  If this EIN had more than 3 physical locations at the end of $$00, copy this page and provide the requested data for all of your locations.</I>","ref_period":"1997 CENSUS"},{"id":"D078FF39-F2A6-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_wording":"  ","responses_id":"D078FF39-F2A7-91A7-E002-F10EC8809F01","keycode":"0088","ag_id_paper":"63C0BD88-3281-42AF-9370-6F3131AF5C0C","ag_id_electronic":"2FFA476D-94E4-B36C-E040-18ACC96034F5","ag_id_dc":"1CB5B524-E906-73B5-E040-18ACC5601885","de_name":"AFFIL_SPL_NAME","ref_period":"1997 CENSUS"},{"id":"D078FF39-F2AA-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_wording":"  ","responses_id":"D078FF39-F2AB-91A7-E002-F10EC8809F01","keycode":"0089","ag_id_paper":"63C0BD88-3281-42AF-9370-6F3131AF5C0C","ag_id_electronic":"2FFA476D-94E4-B36C-E040-18ACC96034F5","ag_id_dc":"1CB5B524-E904-73B5-E040-18ACC5601885","de_name":"AFFIL_SPL_ADDR_STREET","ref_period":"1997 CENSUS"},{"id":"D078FF39-F2AE-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_wording":"  ","responses_id":"D078FF39-F2AF-91A7-E002-F10EC8809F01","keycode":"0090","ag_id_paper":"2CA684E8-8B03-4279-A924-A22B73EAEFEB","ag_id_electronic":"2FFA476D-94DD-B36C-E040-18ACC96034F5","ag_id_dc":"1CB5B524-E8E4-73B5-E040-18ACC5601885","de_name":"AFFIL_SPL_ADDR_CITY","ref_period":"1997 CENSUS"},{"id":"D078FF39-F2B2-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_wording":"  ","responses_id":"D078FF39-F2B3-91A7-E002-F10EC8809F01","keycode":"0091","ag_id_paper":"A5F37A59-592F-461C-A43A-C94D0F370ACD","ag_id_electronic":"2A291202-6FA8-0417-E040-18ACC96076A7","ag_id_dc":"1CB5B524-DC5E-73B5-E040-18ACC5601885","de_name":"AFFIL_SPL_ADDR_ST","ref_period":"1997 CENSUS"},{"id":"D078FF39-F2B2-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_wording":"  ","responses_id":"D078FF39-F2B4-91A7-E002-F10EC8809F01","keycode":"0092","ag_id_paper":"7ACC82BA-84E5-481F-AE4F-3062385EA01F","ag_id_electronic":"2A291202-6FA9-0417-E040-18ACC96076A7","ag_id_dc":"1CB5B524-DC6E-73B5-E040-18ACC5601885","de_name":"AFFIL_SPL_ADDR_ZIP","ref_period":"1997 CENSUS"},{"id":"D078FF39-F2B7-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_wording":"  ","responses_id":"D078FF39-F2B8-91A7-E002-F10EC8809F01","keycode":"0703","ag_id_paper":"3C93B4E7-D0F5-4376-95F6-5CB319AAA7AF","ag_id_electronic":"2FFA476D-94E3-B36C-E040-18ACC96034F5","ag_id_dc":"1CB5B524-E958-73B5-E040-18ACC5601885","de_name":"AFFIL_SPL_NAICS_SELFDSG_WRT","ref_period":"1997 CENSUS"},{"id":"D078FF39-F2BB-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_wording":"Sales, shipments, receipts, or revenue  ","responses_id":"D078FF39-F2BD-91A7-E002-F10EC8809F01","keycode":"0093","ag_id_paper":"07220218-D5F8-491E-AEDB-30B8C90A8533","ag_id_electronic":"2FE0C9D9-3F8C-0CEC-E040-18ACC5605351","ag_id_dc":"1CB5B524-E12C-73B5-E040-18ACC5601885","de_name":"AFFIL_SPL_RCPT_TOT","ref_period":"1997 CENSUS"},{"id":"D078FF39-F2C0-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_wording":"Number of paid employees for pay period including March 12  ","responses_id":"D078FF39-F2C3-91A7-E002-F10EC8809F01","keycode":"0094","ag_id_paper":"71E3C857-D032-4EE0-8DC1-E12343D98F0E","ag_id_electronic":"2FE0C9D9-3F85-0CEC-E040-18ACC5605351","ag_id_dc":"1CB5B524-E7AE-73B5-E040-18ACC5601885","de_name":"AFFIL_SPL_EMP_MAR12","ref_period":"1997 CENSUS"},{"id":"D078FF39-F2C6-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_wording":"First quarter payroll <I>(Jan-Mar, $$00)</I> ","responses_id":"D078FF39-F2CA-91A7-E002-F10EC8809F01","keycode":"0096","ag_id_paper":"07220218-D5F8-491E-AEDB-30B8C90A8533","ag_id_electronic":"2FE0C9D9-3F8C-0CEC-E040-18ACC5605351","ag_id_dc":"1CB5B524-E12A-73B5-E040-18ACC5601885","de_name":"AFFIL_SPL_PAY_QTR1","ref_period":"1997 CENSUS"},{"id":"D078FF39-F2CD-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_wording":"Annual payroll  ","responses_id":"D078FF39-F2CF-91A7-E002-F10EC8809F01","keycode":"0098","ag_id_paper":"07220218-D5F8-491E-AEDB-30B8C90A8533","ag_id_electronic":"2FE0C9D9-3F8C-0CEC-E040-18ACC5605351","ag_id_dc":"1CB5B524-EE2C-73B5-E040-18ACC5601885","de_name":"AFFIL_SPL_PAY_ANN","ref_period":"1997 CENSUS"},{"id":"D078FF39-F2D3-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_wording":"  ","responses_id":"D078FF39-F2D4-91A7-E002-F10EC8809F01","keycode":"0086","ag_id_paper":"63C0BD88-3281-42AF-9370-6F3131AF5C0C","ag_id_electronic":"2FFA476D-94E4-B36C-E040-18ACC96034F5","ag_id_dc":"1CB5B524-EEF4-73B5-E040-18ACC5601885","de_name":"AFFIL_SPL_ADDR_MUN","ref_period":"1997 CENSUS"},{"id":"D078FF39-F2D7-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_wording":"  ","ref_period":"1997 CENSUS"},{"id":"D078FF39-F2DA-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_wording":"  ","ref_period":"1997 CENSUS"},{"id":"D078FF39-F2DD-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_wording":"  ","ref_period":"1997 CENSUS"},{"id":"D078FF39-F2E0-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_wording":"  ","ref_period":"1997 CENSUS"},{"id":"D078FF39-F2E3-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_wording":" <I>(Jan-Mar, $$00)</I> ","ref_period":"1997 CENSUS"},{"id":"D078FF39-F2E6-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_wording":"  ","ref_period":"1997 CENSUS"},{"id":"D078FF39-F2E9-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_wording":"  ","ref_period":"1997 CENSUS"},{"id":"D078FF39-F2EC-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_wording":"  ","ref_period":"1997 CENSUS"},{"id":"D078FF39-F2EF-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_wording":"  ","ref_period":"1997 CENSUS"},{"id":"D078FF39-F2F2-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_wording":"  ","ref_period":"1997 CENSUS"},{"id":"D078FF39-F2F5-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_wording":"  ","ref_period":"1997 CENSUS"},{"id":"D078FF39-F2F8-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_wording":"  ","ref_period":"1997 CENSUS"},{"id":"D078FF39-F2FB-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_wording":"  ","ref_period":"1997 CENSUS"},{"id":"D078FF39-F2FE-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_wording":"  ","ref_period":"1997 CENSUS"},{"id":"D078FF39-F301-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_wording":"  ","ref_period":"1997 CENSUS"},{"id":"D078FF39-F304-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_wording":"  ","ref_period":"1997 CENSUS"},{"id":"014592F3-C945-2E03-E012-186ACB8075B0","title":"OWNERSHIP OR CONTROL","item_wording":"  ","ref_period":"1997 CENSUS"},{"id":"014592F3-C949-2E03-E012-186ACB8075B0","title":"OWNERSHIP OR CONTROL","item_wording":"  ","ref_period":"1997 CENSUS"},{"id":"24F4CCB1-7A07-2231-E040-18ACC9603315","title":"OWNERSHIP OR CONTROL","responses_id":"24F4CCB1-7A0B-2231-E040-18ACC9603315","ag_id_paper":"22756041-82EE-E285-E040-18ACC7601C3A","ag_id_electronic":"22756041-82EF-E285-E040-18ACC7601C3A","ag_id_dc":"1CB5B524-DDA2-73B5-E040-18ACC5601885","de_name":"AFFIL_OWNER","ref_period":"1997 CENSUS"},{"id":"24F4CCB1-7A0C-2231-E040-18ACC9603315","title":"OWNERSHIP OR CONTROL","responses_id":"24F4CCB1-7A10-2231-E040-18ACC9603315","ag_id_paper":"22756041-82EE-E285-E040-18ACC7601C3A","ag_id_electronic":"22756041-82EF-E285-E040-18ACC7601C3A","ag_id_dc":"1CB5B524-DDA4-73B5-E040-18ACC5601885","de_name":"AFFIL_OWNER_STOCK","ref_period":"1997 CENSUS"},{"id":"24F4CCB1-7A11-2231-E040-18ACC9603315","title":"OWNERSHIP OR CONTROL","responses_id":"24F4CCB1-7A15-2231-E040-18ACC9603315","ag_id_paper":"22756041-82EE-E285-E040-18ACC7601C3A","ag_id_electronic":"22756041-82EF-E285-E040-18ACC7601C3A","ag_id_dc":"1CB5B524-DDA0-73B5-E040-18ACC5601885","de_name":"AFFIL_SPL_DOM_OWN","ref_period":"1997 CENSUS"}]
    """

//  def questionItemsTestList : List[QuestionItem] = {
//    Json.parse(storedQItemsJson).as[List[QuestionItem]]
//  }
}*/
//------------- temp for static testing


//-------------

object ItemKinds extends Enumeration {
  val itkCustomContent = Value("CustomContent")
  val itkHeader = Value("Header")
  val itkPagemaster = Value("PageMaster")
  val itkDocument = Value("Document")
}

object MediaKinds extends Enumeration {
  val mkPaper = Value("PAPER")
  val mkElectronic = Value("ELECTRONIC")
}

trait CustomContentItem {
  def id : String
  def text : String
  def dataElementName : String
  def find(anid : String) : Option[CustomContentItem] = {
    if ((anid == id) || (anid == dataElementName))
      Some(this)
    else
      None
  }
}
trait CustomContentParent extends CustomContentItem {
  protected var anItemKind = ItemKinds.itkCustomContent
  protected var aMediaKind = MediaKinds.mkPaper

  def itemKind: ItemKinds.Value = anItemKind

  def mediaKind: MediaKinds.Value = aMediaKind

  var children = List[CustomContentItem]()

  def findItem(anItem: CustomContentParent): Option[CustomContentParent] = {
 //   super.find(anid) match {
//      case Some(obj) => return Some(obj)
//      case None => children.find(cc => cc.find(anid).ne(None))
//    }
    text match {
      case "Item " => { // matching item if item kind and 2nd child wording is same as param
         if ( text.eq(anItem.text) && children(1).text.eq(anItem.children(1).text) )
           Some(this)
        else
           None
         }
      case _ => None
    }
  }

  def matchingItem(anItem : CustomContentParent) : Option[CustomContentParent] = {
     children.foreach(p => {
       val res = p.asInstanceOf[CustomContentParent].findItem(anItem)
       res match {
         case None =>
         case Some(_) => return res
       }
     })
    None
  }

  protected def addItem(anItem: CustomContentItem): CustomContentParent = {
    val lst = new ListBuffer[CustomContentItem]
    lst.appendAll(children)
    lst += anItem
    children = lst.toList
    this
  }

  /*
     appendItem - if item objects are not there, add them otherwise ignore them
     if item classname and item wording is same, ignore it, otherwise add it
     add response items
   */
  def appendItem(anItem: CustomContentParent): CustomContentParent = {
/*    println("appendItem called on "+this.debugString)
    println("   to add: "+anItem.debugString)
    println(" = = = ")
    var target: CustomContentParent = this
 //   find(anItem.id) match {
    children.find(p => p.id.eq(anItem.children.head.id)) match {
      case Some(obj) => target = obj.asInstanceOf[CustomContentParent]
      case None =>
    }
    target.addItem(anItem)
*/
    println("appendItem called find(itemid) and got "+this.find(anItem.id))
    matchingItem(anItem) match {
      case Some(par) => {
        println("have matching item, so appending to this "+par)
        println("have matching item, so appending children of "+anItem)
        // ignore the items, add the new responses
        val lst = new ListBuffer[CustomContentItem]
        lst.appendAll(par.children)
        anItem.children.filter(p => p.getClass.getName.contains("Response")).foreach(resp => {
          lst += resp
          println("adding responses to item list "+resp.dataElementName)
        })
        par.children = lst.toList
      }
      case None =>  addItem(anItem)
    }
    this
  }

  def appendResponse(aResp: CustomContentParent): CustomContentParent = {
    addItem(aResp)
  }
  def childrenString: String = {
    children.mkString(" children(", "\n", ")")
  }

  def debugString: String = {
    getClass.getName+" "+ toString + childrenString
  }

  def hasItem(wording : String) : Boolean = {
    children.find(p => p.isInstanceOf[CCItemWording] && p.toString.equalsIgnoreCase(wording)) match {
      case None => return false
      case Some(x) => return true
    }
 /*   if (id.eq(anID))
      return true
    false*/
  }
}

class CustomContentBase(anID : String, aText : String, aDEName : String, aDescr : String = "") extends CustomContentItem {
  def this(anID : String, aText : String) = {
    this(anID, aText, "")
  }
  protected var descr = aDescr
  def id : String = anID
  def text : String = aText
  def dataElementName : String = aDEName
  override def toString : String = descr + "["+text+"]"// +getClass.getSimpleName //+s"($anID, $aText, $aDEName)"
}

case class CCItemParent(anID : String, aitemKind : ItemKinds.Value, amediaKind : MediaKinds.Value, aDescr : String,
                        aList : List[CustomContentItem]) extends CustomContentParent {
  anItemKind = aitemKind
  aMediaKind = amediaKind
  children = aList
  def id : String = anID
  def text : String = aDescr //itemKind + " - " + mediaKind
  def dataElementName : String = ""
  override def toString() : String = text
}
case class CCItemResponse(anID : String, aDEName : String, aList : List[CustomContentItem]) extends CustomContentParent {
  children = aList
  def id : String = anID
  def text : String = aDEName
  def dataElementName : String = aDEName
  override def toString() : String = "Response ["+text+"]" + children.mkString("[", ",", "]")
  def label : String = {
    children.find(rc => rc.getClass.getSimpleName.equals("CCResponseLabel")) match {
      case Some(lbl) => lbl.text
      case None => ""
    }
  }
}
/*
root is Custom-formatted question
Question number
Title [text]
Item
Wording[text]
Response[dename]
Keycode[]
single line edit control/ceckbox, etc...[DO NOT Place, paper only, etc...]
*/
class CCQuestionTitle(anID : String, aText : String, aDescr : String = "Title ") extends CustomContentBase(anID, aText, "", aDescr)
class CCQuestionWording(anID : String, aText : String, aDescr : String = "Wording ") extends CustomContentBase(anID, aText, "", aDescr)

class CCItemNumber(anID : String, aText : String, aDescr : String = "Number ") extends CustomContentBase(anID, aText, "", aDescr)
class CCItemWording(anID : String, aText : String, aDescr : String = "Wording ") extends CustomContentBase(anID, aText, "", aDescr)
class CCItemHeaderColumnRef(anID : String, aText : String) extends CustomContentBase(anID, aText)
class CCItemHeaderKeycode(anID : String, aText : String) extends CustomContentBase(anID, aText)

class CCResponseLabel(anID : String, aText : String, dataElementName : String, aDescr : String = "Label ") extends CustomContentBase(anID, aText, dataElementName, aDescr)
class CCResponseKeycode(anID : String, aText : String, dataElementName : String, aDescr : String = "Keycode ") extends CustomContentBase(anID, aText, dataElementName, aDescr)
class CCElectronicResponse(anID : String, aText : String, dataElementName : String, aDescr : String = "Electronic ") extends CustomContentBase(anID, aText, dataElementName, aDescr)
class CCPaperResponse(anID : String, aText : String, dataElementName : String, aDescr : String = "Paper ") extends CustomContentBase(anID, aText, dataElementName, aDescr)

//-------------

object CustomContent {
  var currentContent : CustomContentParent = null

  def createQuestionTitle(anID : String, aText : String) : CCQuestionTitle = new CCQuestionTitle(anID, aText)
  def createQuestionWording(anID : String, aText : String) : CCQuestionWording = new CCQuestionWording(anID, aText)

  def createItemNumber(anID : String, aText : String) : CCItemNumber = new CCItemNumber(anID, aText)
  def createItemWording(anID : String, aText : String) : CCItemWording = new CCItemWording(anID, aText)
  def createItemHeaderColumnRef(anID : String, aText : String) : CCItemHeaderColumnRef = new CCItemHeaderColumnRef(anID, aText)
  def createItemHeaderKeycode(anID : String, aText : String) : CCItemHeaderKeycode = new CCItemHeaderKeycode(anID, aText)

  def createResponseLabel(anID : String, aText : String, dataElementName : String) : CCResponseLabel = new CCResponseLabel(anID, aText, dataElementName)
  def createResponseKeycode(anID : String, aText : String, dataElementName : String) : CCResponseKeycode = new CCResponseKeycode(anID, aText, dataElementName)
  def createElectronicResponse(anID : String, aText : String, dataElementName : String) : CCElectronicResponse = new CCElectronicResponse(anID, aText, dataElementName)
  def createPaperResponse(anID : String, aText : String, dataElementName : String) : CCPaperResponse = new CCPaperResponse(anID, aText, dataElementName)

  def createCustomContent(content : CustomContentParent, title : String, quest_wording : Option[String], item_id : String, item_number : Option[String],
                          item_wording : Option[String], hdr_column_ref : Option[String], hdr_keycode : Option[String],
                          responses_id : Option[String], response_label : Option[String], response_instruction : Option[String],
                          keycode : Option[String], ag_id_paper : Option[String], ag_id_electronic : Option[String],
                          ag_id_dc : Option[String], de_name : Option[String], instance_offset : Option[Float],
                          instance_index : Option[Float], question_number : Option[String], ref_period : String) : CustomContentParent = {
    var result = content
    val itemKind = result.itemKind
    val mediaKind = result.mediaKind

    if (result.children.isEmpty) {
      result = result.appendItem( CCItemParent(item_id, itemKind, mediaKind, "Question ", List(
        createQuestionTitle(item_id, title),
        createQuestionWording(item_id, quest_wording.getOrElse(""))
      )
      ))
    }
    println("do we have the item yet? "+result.hasItem(item_id))

    var itemObj : CustomContentParent = null
    if (item_id.nonEmpty) {
 //   if (!result.hasItem(item_id)) {
 //     result = result.appendItem( CCItemParent(item_id, itemKind, mediaKind, "Item ", List(
      itemObj = CCItemParent(item_id, itemKind, mediaKind, "Item ", List(
        createItemNumber(item_id, item_number.getOrElse("")),
        createItemWording(item_id, item_wording.getOrElse(""))
//        createItemHeaderColumnRef(item_id, hdr_column_ref.getOrElse("")),
//        createItemHeaderKeycode(item_id, hdr_keycode.getOrElse(""))
      )
      )
    }
//    println("   parent w second item "+result.debugString)
//    println("")

    var respID = ""
    responses_id match {
      case Some(rid) => respID = rid
      case None => return result
    }

    //    if (instanceOffset <> 0) or (instanceIndex <> 0) then
    //      responseDataElementName := format('%s;%d', [responseDataElementName, instanceOffset + instanceIndex]);
    val dename = de_name.getOrElse("")
//    result = result.appendResponse( CCItemResponse(respID, dename, List(
    val respObj = CCItemResponse(respID, dename, List(
      createResponseLabel(respID, response_label.getOrElse(""), dename),
      createResponseKeycode(respID, keycode.getOrElse("Ã‚Â§"), dename),
      createElectronicResponse(respID, ag_id_electronic.getOrElse(""), dename)
    )
    )

    itemObj.appendResponse(respObj)
    result = result.appendItem(itemObj)
    println(result.debugString)
    println(" ---")
    result
  }

  def loadTest : CustomContentParent = {
    val itemKind = ItemKinds.itkCustomContent
    val mediaKind = MediaKinds.mkElectronic
    var result : CustomContentParent = CCItemParent("QND078FF39-F25C-91A7-E002-F10EC8809F01", itemKind, mediaKind, "Custom formatted question", List())
//    println("base parent "+result.debugString)
    println("")
//      ,{"id":"D078FF39-F264-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_wording":"does your company operate at more than one physical location?  ","ref_period":"1997 CENSUS"}
//      ,{"id":"D078FF39-F267-91A7-E002-F10EC8809F01","title":"OWNERSHIP OR CONTROL","item_number":"B.","item_wording":"Ownership or control  ","ref_period":"1997 CENSUS"}
    result = createCustomContent(result, "OWNERSHIP OR CONTROL", None, "D078FF39-F25C-91A7-E002-F10EC8809F01", Some("A."),
      Some("Is your company owned or controlled by another company  "), None, None,
      Some("D078FF39-F25F-91A7-E002-F10EC8809F01"), Some("Yes"), Some("- <I>Complete lines B and C and return this form with your completed $$00 Economic Census form.</I>"),
      Some("0005"), None, Some("2A291202-6FA4-0417-E040-18ACC96076A7"),
      None, Some("AFFIL_SPL_DOM_OWN_YES"), None,
      None, Some("questnum 1"), "2017")
    result = createCustomContent(result, "OWNERSHIP OR CONTROL", None, "D078FF39-F25C-91A7-E002-F10EC8809F01", Some("A."),
      Some("Is your company owned or controlled by another company  "), None, None,
      Some("D078FF39-F25E-91A7-E002-F10EC8809F01"), Some("No"), Some("- <I>Discard this form (NC-99513) and return your completed $$00 Economic Census form.</I>"),
      Some("0006"), None, Some("2A291202-6FA4-0417-E040-18ACC96076A7"),
      None, Some("AFFIL_SPL_DOM_OWN_NO"), None,
      None, Some("questnum 1"), "2017")
    result = createCustomContent(result, "OWNERSHIP OR CONTROL", None, "D078FF39-F25C-91A7-E002-F10EC8809F01", Some("A."),
      Some("<B>OR</B>  "), None, None,
      None, None, None,
      None, None, None,
      None, None, None,
      None, Some("questnum 1"), "2017")
    result
  }

  def test = {
    val itemKind = ItemKinds.itkCustomContent
    val mediaKind = MediaKinds.mkElectronic
    var parent : CustomContentParent = CCItemParent("parent", itemKind, mediaKind, "parent", List())
    println("base parent "+parent.debugString)
    println("")

    parent = parent.appendItem( CCItemParent("firstchild", itemKind, mediaKind, "Custom formatted question", List(
      createQuestionTitle("firstchild", "first title"),
      createQuestionWording("firstchild", "first wording")
    )
    ))
    println("parent w first child "+parent.debugString)
    println("")

    parent = parent.appendItem( CCItemParent("secondchild", itemKind, mediaKind, "Item ", List(
      createItemNumber("seconditem", "second number"),
      createItemWording("seconditem", "second wording"),
      createItemHeaderColumnRef("seconditem", "second hdr col ref"),
      createItemHeaderKeycode("seconditem", "second hdr keycode")
    )
    ))
    println("parent w second item "+parent.debugString)
    println("")

    val dename = "RECORD_NAME"
    parent = parent.appendResponse( CCItemResponse("thirdresp", dename, List(
      createResponseLabel("thirdresp", "Customer Name:", dename),
      createResponseKeycode("thirdresp", "no keycode", dename),
      createElectronicResponse("thirdresp", "electagid", dename)
    )
    ))
    println("parent w third resp "+parent.debugString)
    println("")
  }

//------------- new methods for filling content tree

  def fillTree(root : TreeItem[CustomContentItem]) = {
    currentContent = loadTest
   //  println(currentContent)
    currentContent.children.foreach(ch => //println("  "+ch)
      addTreeItem(root, ch.asInstanceOf[CustomContentParent])
//     root.getChildren().addAll(
//         getQItem("new1a", "Name", "1a", "Name of individual or establishment", "label"),
//         getQItem("1b", "Street Address", "1b", "address number and name of street", "label")
     )
  }

  def addTreeItem(root : TreeItem[CustomContentItem], cc : CustomContentParent) = {
    val item = new TreeItem(cc.asInstanceOf[CustomContentItem] )
    cc.children.foreach(ch => {
      item.getChildren.add( new TreeItem(ch) )
    })
    root.getChildren.add(item)
  }
/*
  def getQItem(id : String, title : String, num : String, descr : String, ctype : String) : TreeItem[Any] = {
    val qitem = QItem(id, title, 50, 100)
    val item = new TreeItem(qitem)
    val qtitle = new TreeItem[String](title)
    val qnum   = new TreeItem[String](num)
    val qdescr = new TreeItem(descr)
    val qtype  = new TreeItem(ctype)
    item.getChildren().addAll(qtitle, qnum, qdescr, qtype)
    item
  }
*/
}
