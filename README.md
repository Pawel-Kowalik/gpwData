# GPW Data
Spring-Boot application contains RESTful API with polish stock exchange data.  

# Techlogies and frameworks use in project:
<ul>
  <li>Java 8</li>
  <li>Spring-Boot</li>
  <li>Spring Data</li>
  <li>Lombok</li>
  <li>MySQL</li>
  <li>Junit 4</li>
  <li>Swagger2</li>
  <li>Gradle</li>
</ul>

# API
<ul>
  <li>
    <p>
      Get all company names.
    </p>
    <p>
      <b>GET &nbsp&nbsp </b> /gpw-name/all 
    </p>
  </li>
  
  <li>
    <p>
       Get actual company data.
    </p>
    <p>
      <b>GET &nbsp&nbsp</b> /gpw//data/company/{name}
    </p>
  </li>

  <li>
    <p>
      Get company data from today to day in parameter.
    </p>
    <p>
      <b>GET &nbsp&nbsp</b> /gpw/data/company/{name}/last-days/{dayBefore}
    </p>
  </li>

  <li>
    <p>
     Get actual company exchange.
    </p>
    <p>
      <b>GET &nbsp&nbsp</b> /gpw/exchange/company/{name}
    </p>
  </li>

  <li>
    <p>
      Get 5 companies with highest change percent.
    </p>
    <p>
      <b>GET &nbsp&nbsp</b> /gpw/highest-companies-data
    </p>
  </li>

  <li>
    <p>
      Get 5 companies with lowest change percent.
    </p>
    <p>
      <b>GET &nbsp&nbsp</b> /gpw/lowest-companies-data
    </p>
  </li>

</ul>
