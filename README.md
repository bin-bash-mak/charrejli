# Charrejli

## About
During the war, many friends and family fled the country. As the go-to "computer guy"—the one they call only when they need help setting up a printer, pitching their next million-dollar idea, or, occasionally, asking me to hack their ex’s Instagram—I somehow became responsible for keeping their phone lines recharged.  

The irony? I rarely use my own phone and often forget to recharge it. So, naturally, managing half the country's numbers became my problem. To make my life easier, I built this small app to keep track of everything.

I didn't want to use a pbx or a similar solution. But I discovered that on the operator's website, I could get the data that I need. So I wrote scrapers in order to get said data (TODO: In the future I might explore checking the mobile apps APIs as on the web,the pages are mostly server side rendered and parsing html is no fun)

If you are only interested in the scraping parts check:
- [Touch](./src/main/java/com/mohammadalikassem/charrejli/modules/parsers/lb/touch/TouchParser.java)
- [Alfa](./src/main/java/com/mohammadalikassem/charrejli/modules/parsers/lb/alfa/AlfaParser.java)

I also added a telegram bot to keep me update it and remind me if any action is needed of if the consumption surpassed a specific threshold.

## Stack
This App is built using react on the frontend and Spring on the backend with hilla acting as the glue layer between the two of them. A couple of small problems with hilla aside. I really liked the idea behind it. To the point where I created a debug UI to test stuff instead of writing a log to the console

![IM1](./docs/images/screenshots/charrejli-touch-1.png)

And with the power of the springboot ecosystem. the sky is the limit.

## Basically that's it

![IM2](./docs/images/screenshots/charrejli-touch-2.png)

## TODOs:

- [ ] Add actions like transferring balance, recharging etc...
- [ ] Clean and push telegram bot part
- [ ] Add rule engine and link to notification provider
- [ ] Add auth , groups , users permissions etc
- [ ] Add PWA with push notification
- [ ] Store session cookies in db or in an in memory object to avoid having to relogin
