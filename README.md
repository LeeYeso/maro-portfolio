# MARO Android Client

Android client source for the MARO team project. This repository contains the Android app only, not the backend or AI services.

## Local configuration

Create `local.properties` in the repository root (this file is ignored by Git):

```properties
sdk.dir=/path/to/your/Android/sdk
GOOGLE_MAPS_API_KEY=your_google_maps_api_key
SERVER_BASE_URL=https://your-server.example/
```

The Google Maps key is read during Gradle configuration and inserted into the Android manifest at build time. Use a restricted key and do not commit `local.properties`. Android app keys may still be extracted from distributed APKs, so restrict by Android package name, signing certificate SHA-1 and permitted APIs.

The original backend is not included and may no longer be online. Network-driven features require an independently running compatible server. The default endpoint is a nonfunctional placeholder.

## Important

The previously exposed Google Maps API key should be rotated or revoked in Google Cloud Console. Sanitizing new source does not erase copies from the old public repository or its Git history.

## Publishing checklist

- Do not commit `local.properties`, keystores, signing credentials or service account files.
- The backend and AI services are not included. The app cannot demonstrate network-dependent features without a compatible backend.
- Review ownership and permission for shared design assets before publishing.
- Use a new repository with no imported Git history, and review the first commit before pushing.
