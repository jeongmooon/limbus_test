package com.example.test_web.common.youtube.service;

import com.example.test_web.domain.codeInfo.entity.CodeInfo;
import com.example.test_web.domain.codeInfo.repository.CodeInfoRepository;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class YouTubeService {

    private static final String APPLICATION_NAME = "limbusDeckMaker";
    private static final JacksonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    @Value("${youtube.api.key}")
    private String apiKey;

    private final CodeInfoRepository codeInfoRepository;

    @Autowired
    public YouTubeService(CodeInfoRepository codeInfoRepository){
        this.codeInfoRepository = codeInfoRepository;
        System.out.println(apiKey);
    }
    /**
     * 특정 채널의 최신 영상 ID를 가져옴
     */
    public String getLatestVideoByChannel() throws GeneralSecurityException, IOException {

        //String channelId = "UCpqyr6h4RCXCEswHlkSjykA"; // 프로젝트문 채널 ID
        //String channelId = "UCfTGW15PCQnPVAauEBq0iBg"; // 내 유튜브 채널 ID
        String channelId = codeInfoRepository.findById("YOUTUBE_CHANNELID").map(CodeInfo::getValue).orElse("");
        String getPlayListTitle = codeInfoRepository.findById("YOUTUBE_PLAYLIST").map(CodeInfo::getValue).orElse("");
        String getVideoTitle = codeInfoRepository.findById("YOUTUBE_VIDEO_TITLE").map(CodeInfo::getValue).orElse("");

        YouTube youtubeService = new YouTube.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                null
        ).setApplicationName(APPLICATION_NAME).build();

        YouTube.Playlists.List playlistRequest = youtubeService.playlists()
                .list(Collections.singletonList("snippet"))
                .setChannelId(channelId)
                .setMaxResults(50L) // 필요한 만큼 늘릴 수 있음
                .setKey(apiKey);

        PlaylistListResponse response = playlistRequest.execute();
        List<Playlist> playlists = response.getItems();

        for (Playlist playlist : playlists) {
            String title = playlist.getSnippet().getTitle();
            if (title != null && title.equalsIgnoreCase(getPlayListTitle)) {
                // 1. 재생목록에서 영상들 조회
                YouTube.PlaylistItems.List myPlaylistRequest = youtubeService.playlistItems()
                        .list(Collections.singletonList("snippet"))
                        .setPlaylistId(playlist.getId())
                        .setMaxResults(50L)
                        .setKey(apiKey);

                PlaylistItemListResponse playlistResponse = myPlaylistRequest.execute();
                List<PlaylistItem> playlistItems = playlistResponse.getItems();

                if (playlistItems.isEmpty()) return null;

                // 2. videoId 리스트 추출
                List<String> videoIds = playlistItems.stream()
                        .map(item -> item.getSnippet().getResourceId().getVideoId())
                        .collect(Collectors.toList());

                // 3. videoId로 업로드일 정보 조회
                YouTube.Videos.List videosRequest = youtubeService.videos()
                        .list(Collections.singletonList("snippet"))
                        .setId(videoIds)
                        .setKey(apiKey);

                VideoListResponse videoResponse = videosRequest.execute();
                List<Video> videos = videoResponse.getItems();

                if (videos.isEmpty()) return null;

                // 4. 업로드 날짜 기준으로 정렬
                List<Video> filteredVideos = videos.stream()
                        .filter(v -> {
                            String videoTitle = v.getSnippet().getTitle();
                            return videoTitle != null && videoTitle.toLowerCase().contains(getVideoTitle.toLowerCase());
                        })
                        .sorted(Comparator.comparing(v -> v.getSnippet().getPublishedAt().getValue()))
                        .toList();

                if (filteredVideos.isEmpty()) return null;

                Video latest = filteredVideos.get(filteredVideos.size() - 1); // 가장 최근 업로드
                return latest.getId(); // 최신 videoId 반환
            }
        }

        return null;
/*
        // Define and execute the API request
        YouTube.Search.List request = youtubeService.search()
                .list(Collections.singletonList("snippet"))
                .setChannelId(channelId)
                .setOrder("date") // Order by date
                .setMaxResults(1L); // Get only the latest video


        SearchListResponse response = request.setKey(apiKey).execute();
        List<SearchResult> items = response.getItems();

        log.info("Number of items retrieved -> {}", items.size());

        if (!items.isEmpty()) {
            SearchResult item = items.get(0); // 첫 번째 아이템 가져오기
            String videoId = item.getId().getVideoId(); // 비디오 ID 추출
            log.info("Video ID of the latest video -> {}", videoId);
            return videoId;
        } else {
            log.info("No videos found for channel ID {}", channelId);
            return null;
        }
        */
    }

    /**
     * videoId가 쇼츠인지 일반 영상인지 판별
     * @param videoId 유튜브 비디오 ID
     * @return true면 쇼츠, false면 일반 영상
     */
    private boolean isShortsVideo(String videoId) {
        String shortsUrl = "https://www.youtube.com/shorts/" + videoId;

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(shortsUrl).openConnection();
            connection.setInstanceFollowRedirects(false); // 리다이렉트 따라가지 않음
            connection.setRequestMethod("HEAD"); // HEAD로 빠르게

            int responseCode = connection.getResponseCode();

            // 리다이렉트 => 일반 영상
            if (responseCode >= 300 && responseCode < 400) {
                return false; // 일반 영상
            } else {
                return true; // 쇼츠
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false; // 실패 시 일반 영상으로 처리
        }
    }

}