(def test-address
  {:street-address "123 Test Lane"
   :city "Testerville"
   :state "TX"})

(meditations
  "Destructuring is an arbiter: it breaks up arguments"
  (= ":bar:foo" ((fn [[a b]] (str b a))
         [:foo :bar]))

  "Whether in function definitions"
  (= (str "First comes love, "
          "then comes marriage, "
          "then comes Clojure with the baby carriage")
     ((fn [[a b c]] (str (str "First comes " a ", ") (str "then comes " b ", ") (str "then comes " c " with the baby carriage")))
      ["love" "marriage" "Clojure"]))

  "Or in let expressions"
  (= "Rich Hickey aka The Clojurer aka Go Time aka Macro Killah"
     (let [[first-name last-name & aliases]
           (list "Rich" "Hickey" "The Clojurer" "Go Time" "Macro Killah")]
       (str first-name " " last-name " aka " (clojure.string/join " aka " aliases))))

  "You can regain the full argument if you like arguing"
  (= {:original-parts ["Steven" "Hawking"] :named-parts {:first "Steven" :last "Hawking"}}
     (let [[first-name last-name :as full-name] ["Steven" "Hawking"]]
       {:original-parts [first-name last-name] :named-parts {:first first-name :last last-name}}))

  "Break up maps by key"
  (= "123 Test Lane, Testerville, TX"
     (let [{street-address :street-address, city :city, state :state} test-address]
       (format "%s, %s, %s" street-address city state)))

  "Or more succinctly"
  (= "123 Test Lane, Testerville, TX"
     (let [{:keys [street-address city state]} test-address]
       (format "%s, %s, %s" street-address city state)))

  "All together now!"
  (= "Test Testerson, 123 Test Lane, Testerville, TX"
    ((fn [[first-name last-name] addr]
     (let [{:keys [street-address city state]} addr]
        (format "%s %s, %s, %s, %s" first-name last-name street-address city state)))
        ["Test" "Testerson"] test-address)))
