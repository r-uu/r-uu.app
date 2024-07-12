# datamodel
collection of business data models

* person
* contact
  - postal address
  - email address
  - telephone number

## organisation of code

* common
  - business object                    interface
  - business object service            interface
* jpadto
  - mapper interface
  - dto
    - business object dto              implementation
  - jpa
    - business object entity           implementation
    - business object service jpa      interface
    - ee
      - business object repository     implementation
      - business object service jpa ee implementation
    - se
      - business object repository     implementation
      - entity manager producer        implementation