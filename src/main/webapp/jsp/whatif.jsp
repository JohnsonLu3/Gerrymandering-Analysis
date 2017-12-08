<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html><head>
  <meta name="_csrf" content="${_csrf.token}"/>
  <!-- default header name is X-CSRF-TOKEN -->
  <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script type="text/javascript" src="http://code.jquery.com/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="http://netdna.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    <script src="/resources/js/carousel.js"></script>
    <script src="https://d3js.org/d3-path.v1.min.js"></script>
    <script src="https://d3js.org/d3-shape.v1.min.js"></script>
    <script src="http://d3js.org/d3.v3.min.js"></script>
    <script src='https://npmcdn.com/@turf/turf/turf.min.js'></script>
    <script src="/resources/js/data.js"></script>
    <script src="/resources/js/accordionLayout.js"></script>
<!--<script src="/resources/js/whatIfStyling.js"></script>
    <script src="/resources/js/populateWhatIfData.js"></script> -->
    <!-- Bring these links back when ready to load results for tests on super districts
    <script src="/resources/js/lopsidedChart.js"></script>
    <script src="/resources/js/consistentAdvantageChart.js"></script>
    <script src="/resources/js/efficiencyGapChart.js"></script>
    <script src="/resources/js/whatIfMarkerSelection.js"></script>
    -->
    <script src="/resources/js/whatIfEventHandlers.js"></script>
    <script src="/resources/js/initWhatIfMap.js"></script>
    <script src="/resources/js/scatterData.js"></script><!-- this link is test.json as a javascript file-->    
    <script async defer
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAEISuccfcM7cm-Kil-HufVS9mu3kNKrHM&libraries=places,geometry&callback=initializeMap">
    </script>
    <link href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="/resources/style/whatif.css" rel="stylesheet" type="text/css">
  <script>
      $(function () {
          var token = $("meta[name='_csrf']").attr("content");
          var header = $("meta[name='_csrf_header']").attr("content");
          $(document).ajaxSend(function(e, xhr, options) {
              xhr.setRequestHeader(header, token);
          });
      });
  </script>
  </head>
  <body>
  <div class="section">
    <div class="col-md-12 text-right">
      <a href="/admin" class="btn btn-lg btn-primary" style="margin-left: 10px"><i class="fa fa-bar-chart fa-fw fa-lg" style="margin-right:5px"></i>Administrators</a>
     </div>
     </div>
    <div class="section">
      <div class="container">
        <div class="row">
          <div class="col-md-12">
            <ul class="nav nav-tabs">
              <li class="">
                <a href="/">Home</a>
              </li>
              <li>
                <a href="/www/analyze.html">Analyze a State</a>
              </li>
              
              <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Learn More&nbsp;<i class="fa fa-caret-down"></i></a>
                <ul class="dropdown-menu" role="menu">
                  <li>
                    <a href="/www/learn_home.html">Home Page</a>
                  </li>
                  <li class="divider"></li>
                  <li>
                    <a href="/www/learn_tests.html">How Tests Work</a>
                  </li>
                  <li class="divider"></li>
                  <li>
                    <a href="/www/learn_legal.html">Legal Efforts </a>
                  </li>
                </ul>
              </li>
              <li>
                <a href="/www/contact.html">Contact</a>
              </li>
              <li class="active">
                <a href="./whatif.jsp"><i class="fa fa-fw fa-angle-double-right"></i>What If?</a>
              </li>
            </ul>
            <div style="height:35px;"></div>
            <div id="carousel-example" data-interval="false" class="carousel slide" data-ride="carousel">
              <div class="carousel-inner">
                <div class="item active">
                  <img src="https://ununsplash.imgix.net/photo-1413912623716-e6c688db0383?w=1024&amp;q=50&amp;fm=jpg&amp;s=2777ec88322e8725978f0fa956735021">
                  <div class="carousel-caption">
                    <h2>Gerrymandering in the United States</h2>
                  </div>
                </div>
              </div>
              <a class="left carousel-control" href="#carousel-example" data-slide="prev"><i class="icon-prev  fa fa-angle-left"></i></a>
              <a class="right carousel-control" href="#carousel-example" data-slide="next"><i class="icon-next fa fa-angle-right"></i></a>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="section">
      <div class="container">
        <div class="row">
          <div class="col-md-6">
            <div class="col-md-6" id="map" style="width:575px;height:450px;"></div>
              <div>
              <form action="">
                <input id="randomGenerate" type="radio" onclick="initRandomGeneratedSuperDistrict()" value="randomGenerate"> Randomly Generate Superdistrict Example
              </form>
            </div>
            <div>
              <a id = "createButton" class="btn btn-primary" style="; margin-bottom:5px;margin-right:50px">Create Superdistrict</a>
              <a id = "resetButton" class="btn btn-primary" style="margin-bottom:5px;margin-right:40px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Reset &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;     </a>
              <a  id = "undoButton" class="btn btn-primary" style="margin-bottom:5px;margin-right:10px">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;Undo&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</a>
              <a id = "saveButton" class="btn btn-primary disabled" style="margin-right:50px">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;Save &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</a>
              <a id = "cancelButton" class="btn btn-primary" style="margin-right:40px">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;Cancel &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</a>
              <a href = "https://github.com/ysuo85/Gerrymandering-Analysis" id = "sourceButton" class="btn btn-primary">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;Source Code &nbsp; &nbsp; &nbsp; &nbsp;</a>
            </div>
          </div>
          <div class="col-md-6">
            <header class="main-header" role="banner"></header>
            <h1 class="text-primary"></h1>
            <div class="btn-group btn-group-lg">
              <a class="btn btn-primary dropdown-toggle" data-toggle="dropdown">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; Build Your Super District &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;<span class="fa fa-caret-down"></span></a>
              <ul class="dropdown-menu" role="menu">
                <li>
                  <a href="#"></a>
                </li>
              </ul>
            </div>
            <h3></h3>
            <h4>&nbsp; &nbsp; &nbsp; &nbsp; Requirements of Fairness in Accordance with
              Congressional Bill : &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
              &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
              &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
              &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;H.R. 3057</h4>

            <ul>
              <li id="ContiguousTest" class="text-success">All districts shall be contiguous unless it is necessary to include an
              area surrounded by body of water
              </li>
              <li id="RepsVsVotes">
                <br>No district in the state elects exactly 3 representatives and Presidential
                nominee received at least 75% of votes in 2 of the 3 most recent elections
                <br>No district in the state elects exactly 4 representatives and Presidential
                nominee received at least 80% of votes in 2 of the 3 most recent elections
                <br>No district in the state elects exactly 5 representatives and Presidential
                nominee received at least 83% of votes in 2 of the 3 most recent elections
              </li>
              <li id="MinMax">
                <br>The state shall minimize the number of districts electing 4 representatives
                <br>The state shall maximize the number of districts electing 5 representatives
              </li>
              <li>
                <br>The district boundaries shall minimize division of any community
                of interest, municipality, county, or neighborhood, with the exception of relationships with political parties incumbent
                officeholders, or political candidates
              </li>
              <li id="GeoCompactness">
                <br>The districts shall be geographically compact, meaning no nearby population
                must be bypassed for more distant population
                <br>The boundaries of districts shall follow visible geographic features
              </li>

              <li id="SixRepsOrMore">
                <br>State entitled to 6 or more representatives
                <br>It shall establish a number of districts less than the number representatives entitled
                <br>It shall not elect a representative at large (to represent the whole state)
                <br>It shall ensure that districts shall each have equal population per representative
                as nearly as practical
                <br>It shall ensure that the number of representatives to be elected
                per district shall be no fewer than 3, and no greater than 5
              </li>
              
              <li id="StateAtLarge">
                State entitled to 5 or fewer representatives
                <br>All representatives shall be elected at-large, thus eliminating the need
                of drawing Congressional districts within such state
              </li>
            </ul>
            <p></p>
            </div>
         
          
        </div>
      </div>
    </div>
    <div class="container">
      <div class="row">
        <div class="col-md-12">
          <div class="carousel slide multi-item-carousel" id="theCarousel" data-interval="false">
            <a class="left carousel-control" href="#carousel-example" data-slide="prev"><i class="icon-prev fa fa-angle-left"></i></a>
            <a class="right carousel-control" href="#carousel-example" data-slide="next"><i class="icon-next fa fa-angle-right"></i> </a>
          </div>
        </div>
      </div>
    </div>
    <hr>
    <h1 contenteditable="true">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;Recent Redistricting Attempts</h1>
    <div class="section">
    
      <div class="container">
        <a target="_blank" href="/resources/img/efficiency_gap_ideal.jpg"> </a>
        <a class="left carousel-control" href="#carousel-example" data-slide="prev"><i class="icon-prev  fa fa-angle-left"></i></a>
        <a class="right carousel-control" href="#carousel-example" data-slide="next"><i class="icon-next  fa fa-angle-right"></i></a>
        <img src="/resources/img/efficiency_gap_ideal.jpg" alt="Forest" style="width:275px">
        <a target="_blank" href="/resources/img/consistent_advantage.png">
          <img src="/resources/img/consistent_advantage.png" alt="Forest" style="width:275px">
        </a>
        <a target="_blank" href="/resources/img/lopsided3.png">
          <img src="/resources/img/lopsided3.png" alt="Forest" style="width:275px">
        </a>
      </div>
      
  </div>

<hr />   
      <!-- Trigger/Open The Modal -->
            <button id="myBtn">Sources</button>
            <!-- The Modal -->
            <div id="myModal" class="modal">
            <!-- Modal content -->
            <div class="modal-content">
              <div class="modal-header">
                <span class="close">&times;</span>
                <h2 class="text-left">Sources</h2>
              </div>
              <div class="modal-body"> 
                <p class="text-left">
                Brennan Center For Justice. “Gill v. Whitford: Gerrymandering at the Supreme Court.” Gill v. Whitford: Gerrymandering at the Supreme Court | Brennan Center for Justice, New York University School of Law, 2017, www.brennancenter.org/issues/whitford.
                </p>
                <p class="text-left">Brennan Center For Justice. “Benisek v. Lamone.” Benisek v. Lamone | Brennan Center for Justice, New York University School of Law, 2017, www.brennancenter.org/legal-work/benisek-v-lamone-amicus-brief.
                </p>
                <p class="text-left">
                Brennan Center For Justice. “Harris v. Cooper (Amicus Brief).” Harris v. Cooper (Amicus Brief) | Brennan Center for Justice, New York University School of Law, 2017, www.brennancenter.org/legal-work/harris-v-mccrory.
                </p>
                <p class="text-left">
                Brennan Center For Justice. “Common Cause v. Rucho.” Common Cause v. Rucho | Brennan Center for Justice, New York University School of Law, 7 Nov. 2017, www.brennancenter.org/legal-work/common-cause-v-rucho.
                </p>
                <p class="text-left">FairVote.org. “FairVote US House Projects: Monopoly Politics Remains the Right Name.” FairVote, www.fairvote.org/fairvote_us_house_projects_monopoly_politics_remains_the_right_name.</p>                
                <p class="text-left">House - Judiciary; House Administration. “H.R.3057 - Fair Representation Act.” Congress.gov, Rep. Beyer, Donald S., Jr., 26 June 2017, www.congress.gov/bill/115th-congress/house-bill/3057.
                </p>
                <p class="text-left">Levitt, Justin . “Why does it matter?” All About Redistricting , Loyola Law School, 2017, redistricting.lls.edu/why.php.</p>
                <p class="text-left">Stephanopoulos, Nicholas, and Eric McGhee. “Partisan Gerrymandering and the Efficiency Gap.” Http://Chicagounbound.uchicago.edu, University of Chicago Law School, 2014, chicagounbound.uchicago.edu/cgi/viewcontent.cgi?article=1946&context=public_law_and_legal_theory.</p>
                <p class="text-left">Whitaker, Rob. “Princeton Gerrymandering Project | Run Tests.” Princeton University, The Trustees of Princeton University, 2017, gerrymander.princeton.edu/test.</p>                
                <p class="text-left">Whitaker, Rob. “Princeton Gerrymandering Project | About the Tests.” Princeton University, The Trustees of Princeton University, 2017, gerrymander.princeton.edu/info.</p>
                <p class="text-left">Youtube. “Gerrymandering, explained.”Online video clip.YouTube, 14 Nov 2017. Web.</p>         
                
              </div>
              <div class="modal-footer">
                <h3>Gerrymandering in the United States</h3>
              </div>
            </div>
          </div>
          <script src="/resources/static/worksCited.js"></script>
          <link href="/resources/static/worksCited.css" rel="stylesheet" type="text/css">
      <footer class="section section-primary">
        <div class="container">
          <div class="row">
            <div class="col-sm-6">
              <h1 class="text-left">Gerrymandering of the U.S.</h1>
              <p>"Gerrymandering is one of the great political curses of our single-member district plurality system and one that can only truly be lifted by adopting proportional representation."            -- Professor Douglass Amy, Real Choices, New Voices, Columbia University Press,            New York, 1993</p>
            </div>
            <div class="col-sm-6">
              <p class="text-info text-right">
                <br>
                <br>
              </p>
              <div class="row">
                <div class="col-md-12 hidden-lg hidden-md hidden-sm text-left">
                  <a href="#"><i class="fa fa-3x fa-fw fa-instagram text-inverse"></i></a>
                  <a href="#"><i class="fa fa-3x fa-fw fa-twitter text-inverse"></i></a>
                  <a href="#"><i class="fa fa-3x fa-fw fa-facebook text-inverse"></i></a>
                  <a href="#"><i class="fa fa-3x fa-fw fa-github text-inverse"></i></a>
                </div>
              </div>
              <div class="row">
                <div class="col-md-12 hidden-xs text-right">
                  <a href="#"><i class="fa fa-3x fa-fw fa-instagram text-inverse"></i></a>
                  <a href="#"><i class="fa fa-3x fa-fw fa-twitter text-inverse"></i></a>
                  <a href="#"><i class="fa fa-3x fa-fw fa-facebook text-inverse"></i></a>
                  <a href="#"><i class="fa fa-3x fa-fw fa-github text-inverse"></i></a>
                </div>
              </div>
            </div>
          </div>
        </div>
      </footer>
</body></html>
