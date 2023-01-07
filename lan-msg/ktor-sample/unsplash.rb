require 'unsplash'

Unsplash.configure do |config|
  config.application_access_key = ENV['UNSPLASH_ACCESS_KEY']
  config.application_secret = ENV['UNSPLASH_SECRET']
  config.application_redirect_uri = "https://application.com/callback"
  config.utm_source = "client_app"
end

q = ARGV[0]

photo = Unsplash::Photo.random(count: 1, query: q)

File.write("img", photo[0].urls.regular)
