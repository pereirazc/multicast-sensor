Multicast Sensor Server 0.1.2
=========
**Cloud-sensor management service.**

**Changelog:**

* **0.1.2**
 * Added feed alert feature (SCENE situation detection)

* **0.1.1**
 * Added basic authentication management (login and Rest API Security)
 
- **0.1.0**
 * Added sensor/feed CRUD
 * Live feed chart with time window options (last: 1 min, 2min, 5 min, 30min etc) 

**To-dos:**
  - Add persistence management with MongoDB
    - using Drools/SCENE as session manager
  - Regards sensor's timezone to get correct data timestamp
    - register sensor location at Sensor Modal (Google Maps)
  - Add 'sign up' page and account area
    - change name/password
    - cancel account
