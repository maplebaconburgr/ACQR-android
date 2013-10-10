ACQR-android
============

This is an android application that uses a phone camera to take a photo, which is then converted to a QR code that can be read into Animal Crossing: New Leaf (a 3DS game).
Essentially the challenge here is that the AC:NL pattern format requires the image to be indexed with a palette of 15 colours, which themselves must be selected from a pool of around 100 colours.
To do this, we map all the pixels out into the perceptually uniform CIELAB colour space, and use a k-means clustering algorithm to determine the most representitive colours of the image, and we use that for the final result.
Once we have all the colours, we build it back into the binary format I reverse engineered from the game. There are still a few holes here, though, so that app is incomplete. Namely, the conversion only works when seeded with a generated QR code from the same save file.
We then feed the binary data into the Zebra Crossing QR code library, and print out the final QR code.

Installation: just build in eclipse with android plugin, and everything should work.
