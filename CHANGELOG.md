# CHANGELOG

## Version 3.12.0

### Date: 02-May-2023

- Added support for include metadata in asset, entry and query class
- Added support of Region for Azure-EU
- General code and API document improvements
- Breaking changes : PublishType Enums are updated to all CAPS
- Kotlin support removed due to build issue
- EntryModel publish_details parsing issue fixed #30

---

## Version 3.11.0

### Date: 08-Feb-2023

- String locale support added for sync and syncLocale
- Dependencies updated

---

## Version 3.10.3

### Date: 15-Dec-2022

- Minor fixes
- dependencies updated

## Version 3.10.2

### Date: 27-Jun-2022

- 23 Implicit PendingIntent detected in ContentStack Android SDK
- New Region Support - AZURE_NA

---

## Version 3.10.1

### Date: 17-Jan-2022

- Fixed compile issue with target version 31

---

## Version 3.10.0

### Date: 12-Jul-2021

- Added support for updated Utils package

---

## Version 3.9.0

### Date: 05-April-2021

- [Entry]: entry.includeEmbeddedItems() support added
- [Query]: query.includeEmbeddedItems() support added

---

## Version 3.8.2

### Date: 05-March-2021

- Updated X-User-Agent to the header

---

## Version 3.8.1

### Date: 18-FEB-2020

- Increased default timeout time to 30 sec

---

## Version 3.8.0

### Date: 08-DEC-2020

- [Asset]: includeFallback support and includeDimension added
- [AssetQuery]: includeFallback support added
- [Entry]: includeFallback support added
- [Query]: includeFallback support added

---

## Version: 3.7.0

### Date: 15-Nov-2019

- [Stack]: Added support for function getContentType()
- [ContentType]: updated function fetch()
- [Query]: Updated support of whereIn(String KEY, Query queryObject)
- [Query]: Updated support of whereNotIn(String KEY, Query queryObject)

---

## Version: 3.6.1

### Date: 23-Aug-2019

- [Query] - Added support for whereIn(String key) and whereNotIn(String key) methods in Query
- [Config] - Added support for setRegion(ContentstackRegion region) in Config class.

---

## Version: 3.6.0

### Date: 26-July-2019

- [Entry] - Added support for includeReferenceContentTypeUid support in Entry.
- [Query] - Added support for includeReferenceContentTypeUid support in Query.
- [Entry] - setLanguage and getLanguage Deprecated in Entry.
- [Query] - language deprecated in Query
- [Entry] - Added method for getLocale and setLocale(String locale) in Entry
- [Query] - Added method for locale in Query.
- [Query] - Removed deprecated method for includeSchema in Query

---

## Version: 3.5.0

### Date: Apr-12-2019

Change: Added support of below methods in SDK

Below two support from the Config class has been removed permanently

- public void setSSL(boolean isSSL)setSSL()
- public boolean isSSL()

---

## Version: 3.4.1

### Date: Apr-05-2019

Change:

- Removed support for unsafe (HTTP) request.
- Deprecated setSSL(boolean isSSL)setSSL() from config class
- Deprecated isSSL() from config class

---

## Version: 3.4.0

### Date: Oct-22-2018

Change:

New Features:

Added below function support for sync in Stack class

- sync
- syncPaginationToken
- syncToken
- syncFromDate
- syncContentType
- syncLocale
- syncPublishType
- sync(contentType, fromDate, locale, publishType, syncCallBack)
- New Class SyncStack

---

## Version:3.3.0

### Date 15-Dec-2017

New Features:

- Entry
    - added method ‘addParam’
- Query
    - added method 'addParam'
- Asset
    - added method ‘addParam'

---

## Version:3.2.0

### Date 10-Nov-2017

New Features:

- Stack
    - added method 'ImageTransform'
- Query
    - added method 'includeContentType'
- QueryResult
    - added method 'content_type'
- API Modifications:
    - None

API deprecation:

- Query
    - Deprecated method 'includeSchema'

- Bug Fixes:
    - None

---

## Version: 3.1.3

### Date: 28-July-2017

- New Features:
    - None

- API Modifications:
    - None

API deprecation:

None

- Bug Fixes:
    - Cache Policy bug fixed.
    - Cache Policy timeout increased.

---

## Version: 3.1.2

### Date: 24-June-2017

New Features:

- None

API Modifications:

- None

API deprecation:

- Asset

1] Removed getPublishDetails() method. Entry 1] Removed getPublishDetails() method.

Bug Fixes:

- None.

---

## Version: 3.1.1

### Date: 17-May-2017

New Features:

- None

API Modifications:

- None

API deprecation:

None

Bug Fixes:

Added TLSv1.2 support for older android versions.

---

## Version: 3.1.0

### Date: 19-December-2016

New Features:

Added new class Group

Entry
1] Added getAllEntries(String refKey, String refContentType) method.
2] Added getGroups(String key) method.
3] Added getGroup(String key) method.

Asset
1] Added setCachePolicy(CachePolicy policy) method.
2] Added getTags() method.

AssetLibrary
1] Added setCachePolicy(CachePolicy policy) method.

API Modifications:

Asset

1] Renamed getContentType() to getFileType() method. 2] Renamed getUploadUrl() to getUrl() method.

API deprecation:

None

Bug Fixes:

None

---

## Version: 3.0.0

### Date: 27-October-2016

New Features:

Added new class Config

Added new class Asset

Added new class AssetLibrary

Conetntstack 1] Added stack(Context context, String stackApiKey, String accessToken, String
environment, Config config) method.

Stack 1] Added asset() method. 2] Added assetLibrary() method.

Entry
1] Added getPublishDetails() method.

API Modifications:

None

API deprecation:

Conetntstack 1] Removed stack(Context context, String stackApiKey, String accessToken, String
environment, boolean isEnvironmentUid) method.

Stack
1] Removed setVersion(String version) method.
2] Removed setURL(String hostName, boolean isSSL) method.
3] Removed setEnvironment(String environment, boolean isEnvironmentUid) method.
4] Removed isEnvironmentUid() method.
5] Removed getEnvironment() method.
6] Removed getVersion() method.
7] Removed getURL() method

Entry
1] Removed getMetadata() method.

Query
1] Removed afterUid() method.
2] Removed beforeUid() method.

Bug Fixes:

None

---

## Version: 1.0.2

### Date: 28-September-2016

New Features:

None

API Modifications:

None

API deprecation:

None  
Bug Fixes:

Fixed API calls issue for Android 7.0 Nougat.

---

## Version: 1.0.1

### Date: 22-September-2016

New Features:

- None
  API Modifications:
- None

API deprecation:

- None

Bug Fixes:

- Query 1] Fixed search method issue causing improper result.
- Entry 1] Fixed includeReference method issue causing improper result. 2] Fixed fetch method which
  now return response as per environment.

---

## Initial release Version: 1.0.0

### Date: 05-August-2015

Changes

- Introduce content delivery API SDK for Android.
- Points to [contentstack](<[https://www.contentstack.com/](https://www.contentstack.com/)>)
