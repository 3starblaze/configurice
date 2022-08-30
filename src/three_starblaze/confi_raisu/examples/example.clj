(ns three-starblaze.confi-raisu.examples.example)

(def dunst-config
  {"global"
   {"monitor" 0
    "follow" "mouse"
    "geometry" "400x5-30+20"
    "indicate_hidden" "yes"
    "shrink" "no"
    "transparency" 0
    "notification_height" 0
    "padding" 32
    "horizontal_padding" 32
    "frame_width" 1
    "frame_color" "#aaaaaa"
    "separator_color" "frame"
    "sort" "yes"
    "idle_threshold" "120"
    "font" "Hack 10"
    "line_height" 0
    "markup" "full"
    "format" "<span font_desc='Hack 14'><b>%s</b></span>\n<i>%b</i>"
    "alignment" "left"
    "show_age_threshold" 60
    "word_wrap" "yes"
    "ellipsize" "middle"
    "ignore_newline" "no"
    "stack_duplicates" false
    "hide_duplicate_count" false
    "show_indicators" "yes"
    "icon_position" "off"
    "max_icon_size" 32
    "icon_path" "/usr/share/icons/gnome/16x16/status/:/usr/share/icons/gnome/16x16/devices/"
    "sticky_history" "yes"
    "history_length" 20
    "dmenu" "/usr/bin/dmenu -p dunst:"
    "browser" "/usr/bin/sensible-browser"
    "always_run_script" true
    "title" "Dunst"
    "class" "Dunst"
    "startup_notification" false
    "verbosity" "mesg"
    "corner_radius" 0
    "mouse_left_click" "close_current"
    "mouse_middle_click" "do_action"
    "mouse_right_click" "close_all"}

   "shortcuts"
   {"close" "ctrl+space"
    "close_all" "ctrl+shift+space"
    "history" "ctrl+grave"
    "context" "ctrl+shift+period"}

   "urgency_low"
   {"background" "#222222"
    "foreground" "#888888"
    "timeout" 10}

   "urgency_normal"
   {"background" "#282c34"
    "foreground" "#ABB2BF"
    "timeout" 10}

   "urgency_critical"
   {"background" "#900000"
    "foreground" "#ffffff"
    "timeout" 0}})

;; %s is for generated config path
(def dunst-command "dunst -config %s")

(def constants
  {:foreground "#dfdfdf"
   :background "#222"
   :background-alt "#444"
   :primary-color "#ffb52a"
   :secondary-color "#e60053"
   :alert-color "#bd2c40"})

(def polybar-config
  "Simple polybar config with i3 bar."
  {"bar/example"
   {"width" "100%"
    "height" 27
    "fixed-center" false
    "foreground" (:foreground constants)
    "background" (:background constants)
    "padding-left" 0
    "padding-right" 20
    "module-margin-left" 1
    "module-margin-right" 2
    "font-0" "fixed:pixelsize=10;1"
    "font-1" "unifont:fontformat=truetype:size=8:antialias=false;0"
    "font-2" "Siji:pixelsize=10;1"
    "modules-left" "i3"
    "tray-position" "right"
    "tray-padding" 2
    "cursor-click" "pointer"
    "cursor-scroll" "ns-resize"}

   "module/i3"
   ;; Since there is quite a bit of repetition in label-setting code, I added
   ;; a helper to reduce the noise.
   (let [label
         (fn [label-name bg]
           (let [full-name (str "label-" label-name)]
             {full-name "%index%"
              (str full-name "-background") bg
              (str full-name "-padding") 2}))]
     (merge
      {"type" "internal/i3"
       "format" "<label-state> <label-mode>"
       "index-sort" true
       "wrapping-scroll" false

       "label-mode-padding" 2
       "label-mode-foreground" "#000"
       "label-mode-background" (:background-color constants)}
      (label "focused" (:background-alt constants))
      (label "unfocused" (:background-color constants))
      (label "visible" (:background-alt constants))
      (label "urgent" (:alert-color constants))))

   "settings"
   {"screenchange-reload" true}

   "global/wm"
   {"enable-ipc" true
    "margin-top" 5
    "margin-bottom" 5}})

(def rofi-config
  (let [color-map
        {"background-color" (:background constants)
         "color" (:foreground constants)}]
    {"*"
     (merge
      color-map
      {"font" "Hack 10"})

     "window"
     (merge
      color-map
      {"border" "1px"
       "border-color" (:foreground constants)
       "width" "40%"
       "height" "40%"
       "spacing" "1px"
       "children" "[ inputbar, listview ]"})

     "inputbar"
     (merge
      color-map
      {"padding" "10px"
       "children" "[ entry ]"

       "spacing" "0"})

     "entry"
     {"color" "inherit"
      "background-color" "inherit"}

     "element"
     {"padding" "10px"}

     "element selected"
     color-map}))

(def dunst-data
  {:key "dunst"
   :config dunst-config
   :command dunst-command})

(def polybar-data
  {:key "polybar"
   :config polybar-config
   :command "polybar example -config %s"})

(def rofi-data
  {:key "rofi"
   :config rofi-config
   :command "rofi -show drun"})
