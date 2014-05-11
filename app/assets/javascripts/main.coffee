($ "a[data-toggle=modal]").live "click", ->
    target = ($ @).attr('data-target')
url = ($ @).attr('href')
($ target).load(url)