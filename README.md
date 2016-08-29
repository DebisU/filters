![alt tag](http://logoshabm.tmdb.de/240/014432471.JPG)     

- - - -
# all-mercadio--lib-filter

* Filters
 * commonWords
 * url
 * forbiddenWords
 * separators
 * removeSpecificWords
 * multilineSpam
 * endSpam
 * detectPatterns


- - - -
##Filters value:

KEY                 |           VALUE           |     REQUIRED     |     DEFAULT VALUE       |
--------------------|---------------------------|------------------|-------------------------|
commonWords         | No arguments              |       NO         |All spanish prepositions |
url                 | No arguments              |       NO         |        REGEX            |
forbiddenWords      | Texts separated by coma   |       YES        |       Nothing           |
separators          | One separator             |       NO         |         ','             |
removeSpecificWords | Texts separated by coma   |       YES        |       Nothing           |
multilineSpam       | No arguments              |       NO         |  One word on each line  |
endSpam             | No arguments              |       NO         |        REGEX            |
detectPatterns      | No arguments              |       NO         |        REGEX            |

- - - -
## Standard Request example:
```java
        final FilterUseCase filterUseCase = new FilterUseCase();
        final FilterRequest filterRequest = new FilterRequest();
        final Map<String,String> filters = new HashMap<>();

        filters.put("forbiddenwords","tags,keywords");
        filters.put("removespecificwords","Palabras de búsqueda,search");
        filters.put("separators","");
        filters.put("url","");
        filters.put("commonwords","");
        filters.put("endspam","");

        filterRequest.setFiltersToApply(filters);
        filterRequest.setTextToFilter("Text to filter");

        final FilterUseCaseRequest filterUseCaseRequest = new FilterUseCaseRequest(filterRequest);

        FilterUseCaseResponse filterUseCaseResponse = null;
        try {
            filterUseCaseResponse = filterUseCase.execute(filterUseCaseRequest);
        } catch (FilterNotFoundException e) {
            e.printStackTrace();
        }
        final String actual = filterUseCaseResponse.getResult();
```
- - - -